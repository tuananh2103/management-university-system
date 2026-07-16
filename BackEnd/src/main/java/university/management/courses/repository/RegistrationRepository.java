package university.management.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.courses.entity.Registration;
import university.management.students.entity.Student;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByStudentAndSemester(Student student, int semester);
    boolean existsByStudentAndSemester(Student student, int semester);
}
