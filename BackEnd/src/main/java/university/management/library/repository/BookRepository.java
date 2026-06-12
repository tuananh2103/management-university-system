package university.management.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university.management.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdNot(String isbn, Long id);
    long countByStatus(String status);
}
