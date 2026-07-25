package university.management.courses.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size; 

import java.util.List;
/**
 * Minimal DTO for registration requests.
 * Added to ensure the file has a package declaration and a basic structure
 * so the Java compiler and language server can index it.
 */
public record RegisterRequest(
        @NotBlank(message = "Registration number is required") String regNumber,
        @Min(value = 1, message = "Semester must be between 1 and 8")
        @Max(value = 8, message = "Semester must be between 1 and 8") int semester,
        @NotEmpty(message = "Course IDs are required")
        @Size(min = 4, message = "You must register at least 4 courses") List<Integer> courseIds
) {
}