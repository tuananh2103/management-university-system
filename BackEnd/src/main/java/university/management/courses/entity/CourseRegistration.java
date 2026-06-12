package university.management.courses.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "course_registrations",
    uniqueConstraints = @UniqueConstraint(columnNames = {"reg_number", "semester"})
)
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number", nullable = false, length = 50)
    private String regNumber;

    @Column(nullable = false)
    private int semester;

    @ElementCollection
    @CollectionTable(
        name = "registration_courses",
        joinColumns = @JoinColumn(name = "registration_id")
    )
    @Column(name = "course_id")
    private List<Long> courseIds = new ArrayList<>();

    @Column(name = "registered_at", nullable = false, updatable = false)
    private Instant registeredAt = Instant.now();

    public CourseRegistration() {}

    public Long getId() { return id; }
    public String getRegNumber() { return regNumber; }
    public int getSemester() { return semester; }
    public List<Long> getCourseIds() { return courseIds; }
    public Instant getRegisteredAt() { return registeredAt; }

    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setCourseIds(List<Long> courseIds) { this.courseIds = courseIds; }
}
