package university.management.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.courses.entity.Course;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findBySemester(int semester);
    boolean existsByCourseCode(String courseCode);
}
