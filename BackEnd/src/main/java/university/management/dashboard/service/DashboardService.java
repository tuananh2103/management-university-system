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

    public DashboardService(StudentService studentService, BookService bookService, CafeteriaService cafeteriaService) {
        this.studentService = studentService;
        this.bookService = bookService;
        this.cafeteriaService = cafeteriaService;
    }

    public DashboardSummaryDto getSummary() {
        long totalStudents = studentService.countAll();
        long activeStudents = studentService.countByStatus("ACTIVE");
        long inactiveStudents = studentService.countByStatus("INACTIVE");

        long totalBooks = bookService.countAll();
        long availableBooks = bookService.countByStatus("AVAILABLE");
        long borrowedBooks = bookService.countByStatus("BORROWED");

        long totalItems = cafeteriaService.countAll();
        long availableItems = cafeteriaService.countByStatus("AVAILABLE");
        long soldOutItems = cafeteriaService.countByStatus("SOLD_OUT");

        return new DashboardSummaryDto(
                (int) totalStudents, (int) activeStudents, (int) inactiveStudents,
                (int) totalBooks, (int) availableBooks, (int) borrowedBooks,
                (int) totalItems, (int) availableItems, (int) soldOutItems
        );
    }
}
