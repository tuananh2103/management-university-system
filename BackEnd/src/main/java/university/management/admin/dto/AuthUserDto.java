package university.management.admin.dto;

public record AuthUserDto(
        Long id,
        String username,
        String fullName,
        String role
) {
}