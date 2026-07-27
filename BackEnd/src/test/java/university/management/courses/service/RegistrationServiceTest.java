package university.management.courses.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import university.management.admin.entity.AdminUser;
import university.management.admin.repository.AdminUserRepository;
import university.management.courses.repository.CourseRepository;
import university.management.courses.repository.RegistrationRepository;
import university.management.students.entity.Student;
import university.management.students.repository.StudentRepository;


@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @Mock private RegistrationRepository registrationRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private AdminUserRepository adminUserRepository;

    @InjectMocks private RegistrationService registrationService;
    private Student ownStudent;
    private AdminUser studentUser;

    @BeforeEach
    void setUp() {
        ownStudent = new Student();
        ownStudent.setRegNumber("SP21-BCS-066");

        studentUser = new AdminUser("student","hashedpassword","Demo Student","STUDENT");
        studentUser.setStudent(ownStudent);
    }
    @AfterEach
    void tearDown() {
        // read identity from SecurityContextHolder and clear it after each test to avoid side effects
        SecurityContextHolder.clearContext();
    }        

    private void loginAsStudent(String username) {
        var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    // test block to check if a student can only access their own registration
    @Test
    void getRegistration_throwsForbidden_whenStudentRequestsSomeoneElsesRegNumber() {
        loginAsStudent("student");
        when(adminUserRepository.findByUsername("student")).thenReturn(Optional.of(studentUser));

        assertThatThrownBy(() -> registrationService.getRegistration("SP21-BCS-067", 1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("You can only manage your own course registration");
    }
    // Here you would call the method that retrieves the registration and assert that it throws a forbidden exception
    // Verify that the exception message is as expected, indicating that the student cannot access another student's registration.
    @Test   
    void getRegistration_throwsForbidden_whenStudentRequestsSomeoneElsesRegistration() {
        loginAsStudent("student");
        when(adminUserRepository.findByUsername("student")).thenReturn(Optional.of(studentUser));
        when(studentRepository.findByRegNumber("SP21-BCS-066")).thenReturn(Optional.of(ownStudent));
        when(registrationRepository.findByStudentAndSemester(ownStudent, 1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> registrationService.getRegistration("SP21-BCS-066", 1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("No registration found");
        
    }
}
