package university.management.courses.entity;

import jakarta.persistence.*;
import university.management.students.entity.Student;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "registrations",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "semester"})
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private int semester;

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationItem> items = new ArrayList<>();

    @Column(name = "registered_at", nullable = false, updatable = false)
    private Instant registeredAt = Instant.now();

    public Registration() {}

    public void addItem(Course course) {
        RegistrationItem item = new RegistrationItem();
        item.setRegistration(this);
        item.setCourse(course);
        items.add(item);
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public int getSemester() { return semester; }
    public List<RegistrationItem> getItems() { return items; }
    public Instant getRegisteredAt() { return registeredAt; }

    public void setStudent(Student student) { this.student = student; }
    public void setSemester(int semester) { this.semester = semester; }
}
