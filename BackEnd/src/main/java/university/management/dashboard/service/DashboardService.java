package university.management.dashboard.service;

import org.springframework.stereotype.Service;

import university.management.cafe.service.CafeteriaService;
import university.management.dashboard.dto.DashboardSummaryDto;
import university.management.library.service.BookService;
import university.management.students.service.StudentService;

@Service
public class DashboardService {

    private final StudentService studentService;
    private final BookService bookService;
    private final CafeteriaService cafeteriaService;

    public DashboardService(
            StudentService studentService,
            BookService bookService,
            CafeteriaService cafeteriaService
    ) {
        this.studentService = studentService;
        this.bookService = bookService;
        this.cafeteriaService = cafeteriaService;
    }

    public DashboardSummaryDto getSummary() {
        var students = studentService.getAllStudents();
        var books = bookService.getAllBooks();
        var cafeteriaItems = cafeteriaService.getAllItems();

        int activeStudents = (int) students.stream()
                .filter(student -> "ACTIVE".equalsIgnoreCase(student.status()))
                .count();

        int inactiveStudents = (int) students.stream()
                .filter(student -> "INACTIVE".equalsIgnoreCase(student.status()))
                .count();

        int availableBooks = (int) books.stream()
                .filter(book -> "AVAILABLE".equalsIgnoreCase(book.status()))
                .count();

        int borrowedBooks = (int) books.stream()
                .filter(book -> "BORROWED".equalsIgnoreCase(book.status()))
                .count();

        int availableCafeteriaItems = (int) cafeteriaItems.stream()
                .filter(item -> "AVAILABLE".equalsIgnoreCase(item.status()))
                .count();

        int soldOutCafeteriaItems = (int) cafeteriaItems.stream()
                .filter(item -> "SOLD_OUT".equalsIgnoreCase(item.status()))
                .count();

        return new DashboardSummaryDto(
                students.size(),
                activeStudents,
                inactiveStudents,

                books.size(),
                availableBooks,
                borrowedBooks,

                cafeteriaItems.size(),
                availableCafeteriaItems,
                soldOutCafeteriaItems
        );
    }
}