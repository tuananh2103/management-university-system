package university.management.library.dto;

public record CreateBook(
        String isbn,
        String title,
        String author,
        String category,
        int publishedYear,
        String status
) {
}