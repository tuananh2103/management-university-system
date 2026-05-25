package university.management.courses.dto;

import java.util.List;

public record CourseDto(
  int id,
  String courseCode,
  String title,
  int credits,
  int lectureHours,
  int labHours,
  List<String> teachers,
  int semester
) {}
