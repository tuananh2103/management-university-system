package university.management.library.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import university.management.library.dto.BookDto;
import university.management.library.dto.CreateBook;
import university.management.library.dto.UpdateBook;
import university.management.library.entity.Book;
import university.management.library.repository.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
    }

    public BookDto createBook(CreateBook request) {
        validateCreate(request);
        Book book = new Book();
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setPublishedYear(request.publishedYear());
        book.setStatus(request.status() != null ? request.status() : "AVAILABLE");
        return toDto(bookRepository.save(book));
    }

    public BookDto updateBook(Long id, UpdateBook request) {
        validateUpdate(request);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));

        if (bookRepository.existsByIsbnAndIdNot(request.isbn(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ISBN already in use");
        }

        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setPublishedYear(request.publishedYear());
        book.setStatus(request.status());
        return toDto(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public long countAll() { return bookRepository.count(); }
    public long countByStatus(String status) { return bookRepository.countByStatus(status); }

    private void validateCreate(CreateBook request) {
        if (request.isbn() == null || request.isbn().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN is required");
        if (request.title() == null || request.title().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        if (request.author() == null || request.author().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author is required");
        if (bookRepository.existsByIsbn(request.isbn()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ISBN already exists");
    }

    private void validateUpdate(UpdateBook request) {
        if (request.isbn() == null || request.isbn().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN is required");
        if (request.title() == null || request.title().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        if (request.author() == null || request.author().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author is required");
    }

    private BookDto toDto(Book b) {
        return new BookDto(b.getId(), b.getIsbn(), b.getTitle(), b.getAuthor(),
                b.getCategory(), b.getPublishedYear(), b.getStatus());
    }
}
