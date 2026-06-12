package university.management.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.cafe.entity.CafeteriaItem;

public interface CafeteriaItemRepository extends JpaRepository<CafeteriaItem, Long> {
    long countByStatus(String status);
}
