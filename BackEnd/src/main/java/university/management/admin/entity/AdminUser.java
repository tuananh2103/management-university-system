package university.management.admin.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "admin_users")
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 20)
    private String role = "ADMIN";

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public AdminUser() {}

    public AdminUser(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public Instant getCreatedAt() { return createdAt; }
    public void setPassword(String password) { this.password = password; }
}
