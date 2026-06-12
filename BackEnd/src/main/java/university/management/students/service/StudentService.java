package university.management.students.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import university.management.students.dto.CreateStudent;
import university.management.students.dto.StudentDto;
import university.management.students.dto.UpdateStudent;
import university.management.students.entity.Student;
import university.management.students.repository.StudentRepository;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id: " + id));
    }

    public StudentDto createStudent(CreateStudent request) {
        validateCreate(request);
        Student student = new Student();
        student.setStudentCode(request.studentCode());
        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setMajor(request.major());
        student.setYear(request.year());
        student.setStatus(request.status() != null ? request.status() : "ACTIVE");
        return toDto(studentRepository.save(student));
    }

    public StudentDto updateStudent(Long id, UpdateStudent request) {
        validateUpdate(request);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id: " + id));

        if (studentRepository.existsByStudentCodeAndIdNot(request.studentCode(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student code already in use");
        }
        if (studentRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        student.setStudentCode(request.studentCode());
        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setMajor(request.major());
        student.setYear(request.year());
        student.setStatus(request.status());
        return toDto(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    public long countAll() { return studentRepository.count(); }
    public long countByStatus(String status) { return studentRepository.countByStatus(status); }

    private void validateCreate(CreateStudent request) {
        if (request.studentCode() == null || request.studentCode().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student code is required");
        if (request.fullName() == null || request.fullName().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name is required");
        if (request.email() == null || request.email().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        if (studentRepository.existsByStudentCode(request.studentCode()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student code already exists");
        if (studentRepository.existsByEmail(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    private void validateUpdate(UpdateStudent request) {
        if (request.studentCode() == null || request.studentCode().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student code is required");
        if (request.fullName() == null || request.fullName().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name is required");
        if (request.email() == null || request.email().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
    }

    private StudentDto toDto(Student s) {
        return new StudentDto(s.getId(), s.getStudentCode(), s.getFullName(),
                s.getEmail(), s.getMajor(), s.getYear(), s.getStatus());
    }
}
