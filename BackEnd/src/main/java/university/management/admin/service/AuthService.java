package university.management.admin.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import university.management.admin.dto.AuthUserDto;
import university.management.admin.dto.LoginRequest;
import university.management.admin.dto.LoginResponse;

@Service
public class AuthService {

    public LoginResponse login(LoginRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Username is required"
            );
        }

        if (request.password() == null || request.password().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password is required"
            );
        }

        String username = request.username().trim();
        String password = request.password();

        if (username.equals("admin") && password.equals("admin123")) {
            AuthUserDto user = new AuthUserDto(
                    1L,
                    "admin",
                    "System Administrator",
                    "ADMIN"
            );

            return new LoginResponse(
                    generateDemoToken(),
                    user
            );
        }

        if (username.equals("student") && password.equals("student123")) {
            AuthUserDto user = new AuthUserDto(
                    2L,
                    "student",
                    "Demo Student",
                    "STUDENT"
            );

            return new LoginResponse(
                    generateDemoToken(),
                    user
            );
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password"
        );
    }

    private String generateDemoToken() {
        return "demo-token-" + UUID.randomUUID();
    }
}