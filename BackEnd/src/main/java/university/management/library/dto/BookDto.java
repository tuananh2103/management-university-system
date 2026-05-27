package university.management.library.dto;

public record BookDto(
        Long id,
        String isbn,
        String title,
        String author,
        String category,
        int publishedYear,
        String status
) {
}