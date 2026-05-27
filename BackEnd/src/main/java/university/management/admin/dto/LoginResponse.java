package university.management.admin.dto;

public record LoginResponse(
        String token,
        AuthUserDto user
) {
}