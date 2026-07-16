package university.management.courses.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import university.management.courses.dto.RegisterRequest;
import university.management.courses.dto.RegistrationDto;
import university.management.courses.entity.Course;
import university.management.courses.entity.Registration;
import university.management.courses.repository.CourseRepository;
import university.management.courses.repository.RegistrationRepository;
import university.management.students.entity.Student;
import university.management.students.repository.StudentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                                CourseRepository courseRepository,
                                StudentRepository studentRepository) {
        this.registrationRepository = registrationRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public RegistrationDto register(RegisterRequest request) {
        validateRequest(request);

        Student student = findStudentOrThrow(request.regNumber());

        if (registrationRepository.existsByStudentAndSemester(student, request.semester())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Student already registered courses for this semester");
        }

        List<Course> courses = resolveCourses(request.semester(), request.courseIds());

        Registration reg = new Registration();
        reg.setStudent(student);
        reg.setSemester(request.semester());
        for (Course course : courses) {
            reg.addItem(course);
        }

        Registration saved = registrationRepository.save(reg);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public RegistrationDto getRegistration(String regNumber, int semester) {
        Student student = findStudentOrThrow(regNumber);
        return registrationRepository.findByStudentAndSemester(student, semester)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No registration found for this student and semester"));
    }

    public void deleteRegistration(String regNumber, int semester) {
        Student student = findStudentOrThrow(regNumber);
        Registration reg = registrationRepository.findByStudentAndSemester(student, semester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No registration found for this student and semester"));
        registrationRepository.delete(reg);
    }

    private Student findStudentOrThrow(String regNumber) {
        return studentRepository.findByRegNumber(regNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No student found with registration number: " + regNumber));
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

    private List<Course> resolveCourses(int semester, List<Integer> courseIds) {
        Map<Long, Course> offeredById = courseRepository.findBySemester(semester).stream()
                .collect(Collectors.toMap(Course::getId, c -> c));

        return courseIds.stream()
                .map(id -> {
                    Course course = offeredById.get(Long.valueOf(id));
                    if (course == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid course id: " + id);
                    }
                    return course;
                })
                .toList();
    }

    private RegistrationDto toDto(Registration reg) {
        List<Integer> courseIds = reg.getItems().stream()
                .map(item -> item.getCourse().getId().intValue())
                .toList();
        return new RegistrationDto(
                reg.getStudent().getRegNumber(),
                reg.getSemester(),
                courseIds,
                reg.getRegisteredAt().toString()
        );
    }
}
