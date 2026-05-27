package university.management.students.dto;

public record StudentDto(
        Long id,
        String studentCode,
        String fullName,
        String email,
        String major,
        int year,
        String status
) {
}