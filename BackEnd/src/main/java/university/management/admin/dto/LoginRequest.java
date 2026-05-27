package university.management.admin.dto;

public record LoginRequest(
        String username,
        String password
) {
}