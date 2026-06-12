package university.management.courses.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import university.management.courses.dto.RegisterRequest;
import university.management.courses.dto.RegistrationDto;
import university.management.courses.entity.CourseRegistration;
import university.management.courses.repository.CourseRegistrationRepository;
import university.management.courses.repository.CourseRepository;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationService {

    private final CourseRegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;

    public RegistrationService(CourseRegistrationRepository registrationRepository, CourseRepository courseRepository) {
        this.registrationRepository = registrationRepository;
        this.courseRepository = courseRepository;
    }

    public RegistrationDto register(RegisterRequest request) {
        validateRequest(request);

        if (registrationRepository.existsByRegNumberAndSemester(request.regNumber(), request.semester())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Student already registered courses for this semester");
        }

        validateCourseIds(request.semester(), request.courseIds());

        CourseRegistration reg = new CourseRegistration();
        reg.setRegNumber(request.regNumber());
        reg.setSemester(request.semester());
        reg.setCourseIds(request.courseIds().stream().map(Long::valueOf).collect(Collectors.toList()));

        CourseRegistration saved = registrationRepository.save(reg);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public RegistrationDto getRegistration(String regNumber, int semester) {
        return registrationRepository.findByRegNumberAndSemester(regNumber, semester)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No registration found for this student and semester"));
    }

    public void deleteRegistration(String regNumber, int semester) {
        CourseRegistration reg = registrationRepository.findByRegNumberAndSemester(regNumber, semester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No registration found for this student and semester"));
        registrationRepository.delete(reg);
    }

    private void validateRequest(RegisterRequest request) {
        if (request.regNumber() == null || request.regNumber().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration number is required");
        if (request.semester() < 1 || request.semester() > 8)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Semester must be between 1 and 8");
        if (request.courseIds() == null || request.courseIds().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course IDs are required");
        if (request.courseIds().size() < 4)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must register at least 4 courses");

        Set<Integer> uniqueIds = new HashSet<>(request.courseIds());
        if (uniqueIds.size() != request.courseIds().size())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate course IDs are not allowed");
    }

    private void validateCourseIds(int semester, List<Integer> courseIds) {
        Set<Long> offeredIds = courseRepository.findBySemester(semester)
                .stream().map(c -> c.getId()).collect(Collectors.toSet());

        for (Integer id : courseIds) {
            if (!offeredIds.contains(Long.valueOf(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid course id: " + id);
            }
        }
    }

    private RegistrationDto toDto(CourseRegistration reg) {
        List<Integer> courseIds = reg.getCourseIds().stream()
                .map(Long::intValue)
                .collect(Collectors.toList());
        return new RegistrationDto(
                reg.getRegNumber(),
                reg.getSemester(),
                courseIds,
                reg.getRegisteredAt().toString()
        );
    }
}
