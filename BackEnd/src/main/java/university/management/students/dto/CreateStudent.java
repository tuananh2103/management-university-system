package university.management.students.dto;

public record CreateStudent(
        String studentCode,
        String fullName,
        String email,
        String major,
        int year,
        String status
) {
}