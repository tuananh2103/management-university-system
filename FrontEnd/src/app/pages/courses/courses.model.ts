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
 * - lectureHours: optional number of lecture hours per week.
 * - labHours: optional number of lab/tutorial hours per week.
 * - status: optional availability flag; 'OPEN' means students can register.
 */
export interface Course {
  id: number;
  courseCode: string;
  title: string;
  credits: number;
  teachers: string[];
  semester: number;
  lectureHours?: number;
  labHours?: number;
  status?: 'OPEN' | 'CLOSED';
}
