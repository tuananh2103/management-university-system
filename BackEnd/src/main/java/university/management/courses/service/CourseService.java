package university.management.courses.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import university.management.courses.dto.CourseDto;

@Service
public class CourseService {

    public List<CourseDto> getCoursesBySemester(int semester) {
        if (semester < 1 || semester > 8) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Semester must be between 1 and 8"
            );
        }

        String resourcePath = "courses/" + semester + ".txt";
        ClassPathResource resource = new ClassPathResource(resourcePath);

        if (!resource.exists()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Course file not found for semester " + semester
            );
        }

        List<CourseDto> courses = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
                )
        ) {
            List<String> lines = reader.lines().toList();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (line.isBlank()) {
                    continue;
                }

                CourseDto course = parseCourseLine(i, line, semester);
                courses.add(course);
            }

            return courses;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to read courses for semester " + semester,
                    e
            );
        }
    }

    public CourseDto getCourseById(int semester, int courseId) {
        return getCoursesBySemester(semester)
                .stream()
                .filter(course -> course.id() == courseId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid course id: " + courseId
                ));
    }

    private CourseDto parseCourseLine(int id, String line, int semester) {
        String[] parts = line.split(",");

        if (parts.length < 3) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid course line format: " + line
            );
        }

        String courseCode = parts[0].trim();
        String title = parts[1].trim();
        String creditPart = parts[2].trim();

        String teacher = "HYBRID";
        if (parts.length > 3) {
            teacher = parts[3].trim();
        }

        int credits = 0;
        int lectureHours = 0;
        int labHours = 0;

        try {
            // Example: 3 (3 0)
            String[] creditTokens = creditPart.split("\\s+");
            credits = Integer.parseInt(creditTokens[0]);

            int start = creditPart.indexOf("(");
            int end = creditPart.indexOf(")");

            if (start != -1 && end != -1 && end > start) {
                String inside = creditPart.substring(start + 1, end).trim();
                String[] hours = inside.split("\\s+");

                lectureHours = Integer.parseInt(hours[0]);
                labHours = Integer.parseInt(hours[1]);
            }

        } catch (Exception ignored) {
            // If parsing fails, keep 0 values.
        }

        return new CourseDto(
                id,
                courseCode,
                title,
                credits,
                lectureHours,
                labHours,
                List.of(teacher),
                semester
        );
    }
}