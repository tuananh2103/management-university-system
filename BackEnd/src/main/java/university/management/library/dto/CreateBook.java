package university.management.library.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBook(
        @NotBlank(message = "ISBN is required") String isbn,
        @NotBlank(message = "Title is required") String title,
        @NotBlank(message = "Author is required") String author,
        String category,
        int publishedYear,
        String status
) {
}