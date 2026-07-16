package university.management.students.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", unique = true, nullable = false, length = 20)
    private String studentCode;

    @Column(name = "reg_number", unique = true, nullable = false, length = 50)
    private String regNumber;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String major;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Student() {}

    public Long getId() { return id; }
    public String getStudentCode() { return studentCode; }
    public String getRegNumber() { return regNumber; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getMajor() { return major; }
    public int getYear() { return year; }
    public String getStatus() { return status; }

    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setMajor(String major) { this.major = major; }
    public void setYear(int year) { this.year = year; }
    public void setStatus(String status) { this.status = status; }
}
