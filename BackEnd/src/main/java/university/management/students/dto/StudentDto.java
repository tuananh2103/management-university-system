package university.management.students.dto;

public record StudentDto(
        Long id,
        String studentCode,
        String regNumber,
        String fullName,
        String email,
        String major,
        int year,
        String status
) {
}