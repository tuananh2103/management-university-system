package university.management.students.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import university.management.students.dto.CreateStudent;
import university.management.students.dto.StudentDto;
import university.management.students.entity.Student;
import university.management.students.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private CreateStudent request;

    @BeforeEach
    void setUp() {
        request = new CreateStudent("STU010", "SP21-BCS-099", "Test Student",
                "test.student@university.com", "Computer Science", 1, "ACTIVE");
    }
    // Test case to check successful creation of a student
    @Test
    void createStudent_savesAndReturnsDto_whenDataIsUnique() {
        Student saved = new Student();
        saved.setStudentCode(request.studentCode());
        saved.setRegNumber(request.regNumber());
        saved.setFullName(request.fullName());
        saved.setEmail(request.email());
        saved.setMajor(request.major());
        saved.setYear(request.year());
        saved.setStatus(request.status());

        when(studentRepository.existsByStudentCode(request.studentCode())).thenReturn(false);
        when(studentRepository.existsByRegNumber(request.regNumber())).thenReturn(false);
        when(studentRepository.existsByEmail(request.email())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(saved);

        StudentDto result = studentService.createStudent(request);

        assertThat(result.studentCode()).isEqualTo("STU010");
        assertThat(result.email()).isEqualTo("test.student@university.com");
    }
    // Test case to check for duplicate student code
    @Test
    void createStudent_throwsConflict_whenStudentCodeAlreadyExists() {
        when(studentRepository.existsByStudentCode(request.studentCode())).thenReturn(true);

        assertThatThrownBy(() -> studentService.createStudent(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Student code already exists");
    }
}