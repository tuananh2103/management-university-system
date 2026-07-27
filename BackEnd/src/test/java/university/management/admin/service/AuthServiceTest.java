package university.management.admin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import university.management.admin.dto.LoginRequest;
import university.management.admin.entity.AdminUser;
import university.management.admin.repository.AdminUserRepository;
import university.management.admin.security.JwtService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AdminUserRepository adminUserRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private AdminUser adminUser;

    @BeforeEach
    void setUp() {
        adminUser = new AdminUser("admin", "hashed-password", "System Administrator", "ADMIN");
    }
    // Test case to check successful login
    @Test
    void login_returnsToken_whenCredentialsAreCorrect() {
        when(adminUserRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));
        when(passwordEncoder.matches("admin123", "hashed-password")).thenReturn(true);
        when(jwtService.generateToken(adminUser)).thenReturn("fake-jwt-token");

        var response = authService.login(new LoginRequest("admin", "admin123"));

        assertThat(response.token()).isEqualTo("fake-jwt-token");
        assertThat(response.user().username()).isEqualTo("admin");
    }
    // Additional test case to check for wrong password
    @Test
    void login_throwsUnauthorized_whenPasswordIsWrong() {
        when(adminUserRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));
        when(passwordEncoder.matches("wrong-password", "hashed-password")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(new LoginRequest("admin", "wrong-password")))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid username or password");
    }
    // Additional test case to check for non-existent username
    @Test
    void login_throwsUnauthorized_whenUsernameDoesNotExist() {
        when(adminUserRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(new LoginRequest("ghost", "whatever")))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid username or password");
    }
}