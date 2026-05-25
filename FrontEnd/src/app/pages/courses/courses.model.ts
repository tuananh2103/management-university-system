/**
 * Represents a single course offered in the university catalogue.
 *
 * Fields:
 * - id: numeric identifier unique within a semester's course list.
 * - courseCode: short code used in timetables (e.g. CSC101).
 * - title: human readable course title.
 * - credits: credit weight of the course.
 * - teachers: list of teacher names or 'HYBRID' if online/hybrid.
 * - semester: semester number the course is offered in (1..8).
 **/ 
export interface Course {
  id: number;
  courseCode: string;
  title: string;
  credits: number;
  lectureHours: number;
  labHours: number;
  teachers: string[];
  semester: number;
}
export interface CourseRegistration {
  regNumber: string;
  semester: number;
  courseIds: number[];
  createdAt: string;
}

export interface RegisterCoursesRequest {
  regNumber: string;
  semester: number;
  courseIds: number[];
}