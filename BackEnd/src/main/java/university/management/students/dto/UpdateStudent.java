package university.management.students.dto;

public record UpdateStudent(
        String studentCode,
        String regNumber,
        String fullName,
        String email,
        String major,
        int year,
        String status
) {
}