package university.management.courses.service;

import tools.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import university.management.courses.dto.CourseDto;
import university.management.courses.dto.RegisterRequest;
import university.management.courses.dto.RegistrationDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegistrationService {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;

    public RegistrationService(CourseService courseService, ObjectMapper objectMapper) {
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    public RegistrationDto register(RegisterRequest request) {
        validateRequest(request);

        Path filePath = getRegistrationFilePath(request.regNumber(), request.semester());

        if (Files.exists(filePath)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student already registered courses for this semester"
            );
        }

        List<Integer> courseIds = request.courseIds();

        validateCourseIds(request.semester(), courseIds);

        RegistrationDto registration = new RegistrationDto(
                request.regNumber(),
                request.semester(),
                courseIds,
                Instant.now().toString()
        );

        try {
            Files.createDirectories(filePath.getParent());
            objectMapper.writeValue(filePath.toFile(), registration);
            return registration;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save registration",
                    e
            );
        }
    }

    public RegistrationDto getRegistration(String regNumber, int semester) {
        Path filePath = getRegistrationFilePath(regNumber, semester);

        if (!Files.exists(filePath)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No registration found for this student and semester"
            );
        }

        try {
            return objectMapper.readValue(filePath.toFile(), RegistrationDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to read registration",
                    e
            );
        }
    }

    public void deleteRegistration(String regNumber, int semester) {
        Path filePath = getRegistrationFilePath(regNumber, semester);

        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete registration",
                    e
            );
        }
    }

    private void validateRequest(RegisterRequest request) {
        if (request.regNumber() == null || request.regNumber().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Registration number is required"
            );
        }

        if (request.semester() < 1 || request.semester() > 8) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Semester must be between 1 and 8"
            );
        }

        if (request.courseIds() == null || request.courseIds().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Course IDs are required"
            );
        }

        if (request.courseIds().size() < 4) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You must register at least 4 courses"
            );
        }

        Set<Integer> uniqueIds = new HashSet<>(request.courseIds());

        if (uniqueIds.size() != request.courseIds().size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Duplicate course IDs are not allowed"
            );
        }
    }

    private void validateCourseIds(int semester, List<Integer> courseIds) {
        List<CourseDto> offeredCourses = courseService.getCoursesBySemester(semester);

        Set<Integer> offeredIds = new HashSet<>();

        for (CourseDto course : offeredCourses) {
            offeredIds.add(course.id());
        }

        for (Integer id : courseIds) {
            if (!offeredIds.contains(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid course id: " + id
                );
            }
        }
    }

    private Path getRegistrationFilePath(String regNumber, int semester) {
        String safeRegNumber = regNumber.replaceAll("[^a-zA-Z0-9_-]", "_");

        return Paths.get(
                "students_data",
                "courses",
                safeRegNumber + "_sem" + semester + ".json"
        );
    }
}