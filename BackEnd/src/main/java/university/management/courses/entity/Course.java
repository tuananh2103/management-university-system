package university.management.courses.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", unique = true, nullable = false, length = 20)
    private String courseCode;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private int credits;

    @Column(name = "lecture_hours", nullable = false)
    private int lectureHours;

    @Column(name = "lab_hours", nullable = false)
    private int labHours;

    @Column(columnDefinition = "TEXT")
    private String teachers;

    @Column(nullable = false)
    private int semester;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Course() {}

    public Long getId() { return id; }
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public int getLectureHours() { return lectureHours; }
    public int getLabHours() { return labHours; }
    public String getTeachers() { return teachers; }
    public int getSemester() { return semester; }

    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setLectureHours(int lectureHours) { this.lectureHours = lectureHours; }
    public void setLabHours(int labHours) { this.labHours = labHours; }
    public void setTeachers(String teachers) { this.teachers = teachers; }
    public void setSemester(int semester) { this.semester = semester; }
}
