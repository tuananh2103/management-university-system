package university.management.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import university.management.admin.entity.AdminUser;
import university.management.admin.repository.AdminUserRepository;
import university.management.cafe.entity.CafeteriaItem;
import university.management.cafe.repository.CafeteriaItemRepository;
import university.management.courses.entity.Course;
import university.management.courses.repository.CourseRepository;
import university.management.library.entity.Book;
import university.management.library.repository.BookRepository;
import university.management.students.entity.Student;
import university.management.students.repository.StudentRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final AdminUserRepository adminUserRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final CafeteriaItemRepository cafeteriaItemRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AdminUserRepository adminUserRepository,
                      StudentRepository studentRepository,
                      BookRepository bookRepository,
                      CafeteriaItemRepository cafeteriaItemRepository,
                      CourseRepository courseRepository,
                      PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;
        this.cafeteriaItemRepository = cafeteriaItemRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedAdminUsers();
        seedStudents();
        seedBooks();
        seedCafeteriaItems();
        seedCourses();
    }

    private void seedAdminUsers() {
        if (adminUserRepository.count() > 0) return;
        adminUserRepository.saveAll(List.of(
                new AdminUser("admin", passwordEncoder.encode("admin123"), "System Administrator", "ADMIN"),
                new AdminUser("student", passwordEncoder.encode("student123"), "Demo Student", "STUDENT")
        ));
    }

    private void seedStudents() {
        if (studentRepository.count() > 0) return;
        Student s1 = new Student();
        s1.setStudentCode("STU001"); s1.setFullName("Nguyen Van An");
        s1.setEmail("an.nguyen@university.com"); s1.setMajor("Computer Science");
        s1.setYear(1); s1.setStatus("ACTIVE");

        Student s2 = new Student();
        s2.setStudentCode("STU002"); s2.setFullName("Tran Thi Binh");
        s2.setEmail("binh.tran@university.com"); s2.setMajor("Software Engineering");
        s2.setYear(2); s2.setStatus("ACTIVE");

        Student s3 = new Student();
        s3.setStudentCode("STU003"); s3.setFullName("Le Minh Chau");
        s3.setEmail("chau.le@university.com"); s3.setMajor("Information Systems");
        s3.setYear(3); s3.setStatus("INACTIVE");

        studentRepository.saveAll(List.of(s1, s2, s3));
    }

    private void seedBooks() {
        if (bookRepository.count() > 0) return;
        Book b1 = new Book(); b1.setIsbn("978-0134685991"); b1.setTitle("Effective Java");
        b1.setAuthor("Joshua Bloch"); b1.setCategory("Programming"); b1.setPublishedYear(2018); b1.setStatus("AVAILABLE");

        Book b2 = new Book(); b2.setIsbn("978-1617294945"); b2.setTitle("Spring in Action");
        b2.setAuthor("Craig Walls"); b2.setCategory("Backend"); b2.setPublishedYear(2022); b2.setStatus("AVAILABLE");

        Book b3 = new Book(); b3.setIsbn("978-1491950357"); b3.setTitle("Building Microservices");
        b3.setAuthor("Sam Newman"); b3.setCategory("Architecture"); b3.setPublishedYear(2021); b3.setStatus("BORROWED");

        bookRepository.saveAll(List.of(b1, b2, b3));
    }

    private void seedCafeteriaItems() {
        if (cafeteriaItemRepository.count() > 0) return;
        CafeteriaItem i1 = new CafeteriaItem(); i1.setName("Chicken Sandwich");
        i1.setCategory("Food"); i1.setPrice(BigDecimal.valueOf(4.50));
        i1.setDescription("Fresh sandwich with chicken, salad and sauce."); i1.setStatus("AVAILABLE");

        CafeteriaItem i2 = new CafeteriaItem(); i2.setName("Orange Juice");
        i2.setCategory("Drink"); i2.setPrice(BigDecimal.valueOf(2.20));
        i2.setDescription("Fresh orange juice."); i2.setStatus("AVAILABLE");

        CafeteriaItem i3 = new CafeteriaItem(); i3.setName("Chocolate Muffin");
        i3.setCategory("Snack"); i3.setPrice(BigDecimal.valueOf(2.80));
        i3.setDescription("Soft muffin with chocolate chips."); i3.setStatus("SOLD_OUT");

        cafeteriaItemRepository.saveAll(List.of(i1, i2, i3));
    }

    private void seedCourses() {
        if (courseRepository.count() > 0) return;
        for (int semester = 1; semester <= 8; semester++) {
            seedCoursesFromFile(semester);
        }
    }

    private void seedCoursesFromFile(int semester) {
        String resourcePath = "courses/" + semester + ".txt";
        ClassPathResource resource = new ClassPathResource(resourcePath);
        if (!resource.exists()) return;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            List<String> lines = reader.lines().toList();
            for (String line : lines) {
                line = line.trim();
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String courseCode = parts[0].trim();
                if (courseRepository.existsByCourseCode(courseCode)) continue;

                String title = parts[1].trim();
                String creditPart = parts[2].trim();
                String teacher = parts.length > 3 ? parts[3].trim() : "HYBRID";

                int credits = 0, lectureHours = 0, labHours = 0;
                try {
                    String[] creditTokens = creditPart.split("\\s+");
                    credits = Integer.parseInt(creditTokens[0]);
                    int start = creditPart.indexOf("("), end = creditPart.indexOf(")");
                    if (start != -1 && end > start) {
                        String[] hours = creditPart.substring(start + 1, end).trim().split("\\s+");
                        lectureHours = Integer.parseInt(hours[0]);
                        labHours = Integer.parseInt(hours[1]);
                    }
                } catch (Exception ignored) {}

                Course course = new Course();
                course.setCourseCode(courseCode);
                course.setTitle(title);
                course.setCredits(credits);
                course.setLectureHours(lectureHours);
                course.setLabHours(labHours);
                course.setTeachers(teacher);
                course.setSemester(semester);
                courseRepository.save(course);
            }
        } catch (Exception ignored) {}
    }
}
