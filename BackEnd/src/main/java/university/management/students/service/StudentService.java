package university.management.students.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import university.management.students.dto.CreateStudent;
import university.management.students.dto.StudentDto;
import university.management.students.dto.UpdateStudent;

@Service
public class StudentService {

    private final List<StudentDto> students = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public StudentService() {
        students.add(new StudentDto(
                1L,
                "STU001",
                "Nguyen Van An",
                "an.nguyen@university.com",
                "Computer Science",
                1,
                "ACTIVE"
        ));

        students.add(new StudentDto(
                2L,
                "STU002",
                "Tran Thi Binh",
                "binh.tran@university.com",
                "Software Engineering",
                2,
                "ACTIVE"
        ));

        students.add(new StudentDto(
                3L,
                "STU003",
                "Le Minh Chau",
                "chau.le@university.com",
                "Information Systems",
                3,
                "INACTIVE"
        ));
    }

    public List<StudentDto> getAllStudents() {
        return students;
    }

    public StudentDto getStudentById(Long id) {
        return students.stream()
                .filter(student -> student.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student not found with id: " + id
                ));
    }

    public StudentDto createStudent(CreateStudent request) {
        validateCreate(request);

        Long id = idGenerator.getAndIncrement();

        StudentDto student = new StudentDto(
                id,
                request.studentCode(),
                request.fullName(),
                request.email(),
                request.major(),
                request.year(),
                request.status()
        );

        students.add(student);

        return student;
    }

    public StudentDto updateStudent(Long id, UpdateStudent request) {
        validateUpdate(request);

        for (int i = 0; i < students.size(); i++) {
            StudentDto current = students.get(i);

            if (current.id().equals(id)) {
                StudentDto updated = new StudentDto(
                        id,
                        request.studentCode(),
                        request.fullName(),
                        request.email(),
                        request.major(),
                        request.year(),
                        request.status()
                );

                students.set(i, updated);

                return updated;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Student not found with id: " + id
        );
    }

    public void deleteStudent(Long id) {
        boolean removed = students.removeIf(student -> student.id().equals(id));

        if (!removed) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found with id: " + id
            );
        }
    }

    private void validateCreate(CreateStudent request) {
        if (request.studentCode() == null || request.studentCode().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student code is required"
            );
        }

        if (request.fullName() == null || request.fullName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Full name is required"
            );
        }

        if (request.email() == null || request.email().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email is required"
            );
        }
    }

    private void validateUpdate(UpdateStudent request) {
        if (request.studentCode() == null || request.studentCode().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student code is required"
            );
        }

        if (request.fullName() == null || request.fullName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Full name is required"
            );
        }

        if (request.email() == null || request.email().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email is required"
            );
        }
    }
}