package university.management.courses.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "registration_items",
    uniqueConstraints = @UniqueConstraint(columnNames = {"registration_id", "course_id"})
)
public class RegistrationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public RegistrationItem() {}

    public Long getId() { return id; }
    public Registration getRegistration() { return registration; }
    public Course getCourse() { return course; }

    public void setRegistration(Registration registration) { this.registration = registration; }
    public void setCourse(Course course) { this.course = course; }
}
