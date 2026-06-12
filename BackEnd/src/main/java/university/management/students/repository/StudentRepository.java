package university.management.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.students.entity.Student;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentCode(String studentCode);
    boolean existsByEmail(String email);
    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    long countByStatus(String status);
}
