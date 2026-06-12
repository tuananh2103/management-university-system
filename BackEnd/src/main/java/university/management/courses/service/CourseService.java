package university.management.courses.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import university.management.courses.dto.CourseDto;
import university.management.courses.entity.Course;
import university.management.courses.repository.CourseRepository;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDto> getCoursesBySemester(int semester) {
        if (semester < 1 || semester > 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Semester must be between 1 and 8");
        }
        return courseRepository.findBySemester(semester).stream()
                .map(this::toDto)
                .toList();
    }

    public CourseDto getCourseById(int semester, int courseId) {
        return courseRepository.findById((long) courseId)
                .filter(c -> c.getSemester() == semester)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found: " + courseId));
    }

    public CourseDto toDto(Course c) {
        List<String> teachers = c.getTeachers() != null
                ? Arrays.asList(c.getTeachers().split(","))
                : List.of("HYBRID");
        return new CourseDto(
                c.getId().intValue(),
                c.getCourseCode(),
                c.getTitle(),
                c.getCredits(),
                c.getLectureHours(),
                c.getLabHours(),
                teachers,
                c.getSemester()
        );
    }
}
