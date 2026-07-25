package university.management.students.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateStudent(
        @NotBlank(message = "Student code is required") String studentCode,
        @NotBlank(message = "Registration number is required") String regNumber,
        @NotBlank(message = "Full name is required") String fullName,
        @NotBlank(message = "Email is required") @Email(message = "Email must be a valid address") String email,
        @NotBlank(message = "Major is required") String major,
        @Min(value = 1, message = "Year must be between 1 and 5") @Max(value = 5, message = "Year must be between 1 and 5") int year,
        String status
) {
}