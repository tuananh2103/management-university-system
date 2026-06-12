package university.management.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.admin.entity.AdminUser;
import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
