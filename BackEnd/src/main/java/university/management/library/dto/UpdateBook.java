package university.management.library.dto;

public record UpdateBook(
        String isbn,
        String title,
        String author,
        String category,
        int publishedYear,
        String status
) {
}