package university.management.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.courses.entity.CourseRegistration;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    Optional<CourseRegistration> findByRegNumberAndSemester(String regNumber, int semester);
    boolean existsByRegNumberAndSemester(String regNumber, int semester);
}
