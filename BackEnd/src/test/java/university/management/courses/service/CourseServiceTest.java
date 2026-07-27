package university.management.courses.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import university.management.courses.entity.Course;
import university.management.courses.entity.Teacher;
import university.management.courses.repository.CourseRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;
    // Test case to check successful retrieval of courses by semester
    @Test
    void getCoursesBySemester_returnsMappedList() {
        Teacher teacher = new Teacher("Mr. Smith");
        Course course = new Course();
        ReflectionTestUtils.setField(course, "id", 1L);
        course.setCourseCode("CSC101");
        course.setTitle("Intro to Programming");
        course.setCredits(3);
        course.setLectureHours(3);
        course.setLabHours(0);
        course.setTeacher(teacher);
        course.setSemester(1);

        when(courseRepository.findBySemester(1)).thenReturn(List.of(course));

        var result = courseService.getCoursesBySemester(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).courseCode()).isEqualTo("CSC101");
        assertThat(result.get(0).teachers()).containsExactly("Mr. Smith");
    }
    //  Test case to check for semester out of range
    @Test
    void getCoursesBySemester_throwsBadRequest_whenSemesterOutOfRange() {
        assertThatThrownBy(() -> courseService.getCoursesBySemester(9))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Semester must be between 1 and 8");
    }
}