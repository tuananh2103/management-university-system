package university.management.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import university.management.library.dto.CreateBook;
import university.management.library.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;   

    private CreateBook book;

    @BeforeEach
    void setUp() {
        book = new CreateBook("BC001", "Test Book", 
        "Test Author", "Fiction", 
        2023, "AVAILABLE");
    }
    // Test case to check successful creation of a book
    @Test
    void createBook_savesAndReturnsDto_whenDataIsUnique() {
        when(bookRepository.existsByIsbn(book.isbn())).thenReturn(false);
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = bookService.createBook(book);

        assertThat(result.isbn()).isEqualTo("BC001");
        assertThat(result.title()).isEqualTo("Test Book");

        when(bookRepository.existsByIsbn(book.isbn())).thenReturn(true);
        assertThatThrownBy(() -> bookService.createBook(book))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ISBN already exists");
    }
    // Test case to check for duplicate ISBN
    @Test
    void createBook_throwsConflict_whenIsbnAlreadyExists() {

        when(bookRepository.existsByIsbn(book.isbn())).thenReturn(true);
        assertThatThrownBy(() -> bookService.createBook(book))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ISBN already exists");
    }
}
