package university.management.admin.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import university.management.admin.dto.AuthUserDto;
import university.management.admin.dto.LoginRequest;
import university.management.admin.dto.LoginResponse;
import university.management.admin.entity.AdminUser;
import university.management.admin.repository.AdminUserRepository;
import university.management.admin.security.JwtService;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        if (request.username() == null || request.username().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        if (request.password() == null || request.password().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");

        AdminUser user = adminUserRepository.findByUsername(request.username().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        AuthUserDto userDto = new AuthUserDto(user.getId(), user.getUsername(), user.getFullName(), user.getRole());
        return new LoginResponse(token, userDto);
    }
}
