package university.management.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import university.management.library.dto.BookDto;
import university.management.library.dto.CreateBook;
import university.management.library.dto.UpdateBook;

@Service
public class BookService {

    private final List<BookDto> books = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public BookService() {
        books.add(new BookDto(
                1L,
                "978-0134685991",
                "Effective Java",
                "Joshua Bloch",
                "Programming",
                2018,
                "AVAILABLE"
        ));

        books.add(new BookDto(
                2L,
                "978-1617294945",
                "Spring in Action",
                "Craig Walls",
                "Backend",
                2022,
                "AVAILABLE"
        ));

        books.add(new BookDto(
                3L,
                "978-1491950357",
                "Building Microservices",
                "Sam Newman",
                "Architecture",
                2021,
                "BORROWED"
        ));
    }

    public List<BookDto> getAllBooks() {
        return books;
    }

    public BookDto getBookById(Long id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found with id: " + id
                ));
    }

    public BookDto createBook(CreateBook request) {
        validateCreateRequest(request);

        Long id = idGenerator.getAndIncrement();

        BookDto book = new BookDto(
                id,
                request.isbn(),
                request.title(),
                request.author(),
                request.category(),
                request.publishedYear(),
                request.status()
        );

        books.add(book);

        return book;
    }

    public BookDto updateBook(Long id, UpdateBook request) {
        validateUpdateRequest(request);

        for (int i = 0; i < books.size(); i++) {
            BookDto current = books.get(i);

            if (current.id().equals(id)) {
                BookDto updated = new BookDto(
                        id,
                        request.isbn(),
                        request.title(),
                        request.author(),
                        request.category(),
                        request.publishedYear(),
                        request.status()
                );

                books.set(i, updated);

                return updated;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Book not found with id: " + id
        );
    }

    public void deleteBook(Long id) {
        boolean removed = books.removeIf(book -> book.id().equals(id));

        if (!removed) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book not found with id: " + id
            );
        }
    }

    private void validateCreateRequest(CreateBook request) {
        if (request.isbn() == null || request.isbn().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ISBN is required"
            );
        }

        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title is required"
            );
        }

        if (request.author() == null || request.author().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Author is required"
            );
        }
    }

    private void validateUpdateRequest(UpdateBook request) {
        if (request.isbn() == null || request.isbn().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ISBN is required"
            );
        }

        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title is required"
            );
        }

        if (request.author() == null || request.author().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Author is required"
            );
        }
    }
}