package university.management.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.courses.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByFullName(String fullName);
}
