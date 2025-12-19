import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Course } from './courses.model';
import { CourseRegistrationStore } from './courses-registration.store';
import { COURSES_BY_SEMESTER } from './courses.data';

/**
 * Main component for the Courses page. This standalone component shows
 * a list of offered courses for a selected semester and allows the
 * student (identified by `regNumber`) to select and register courses.
 *
 * Notes:
 * - The component uses `CourseRegistrationStore` which persists to
 *   `localStorage` for demo purposes only.
 */
@Component({
  selector: 'app-courses',
  standalone: true, // stand alone component no need NgModule,import dependency direct in component
  imports: [CommonModule, FormsModule],
  templateUrl: './courses.html',
  styleUrl: './courses.scss',
})
export class CoursesComponent implements OnInit {
  // Example student registration number used for demo purposes
  regNumber = 'SP21-BCS-066';

  /** Search query used to filter the course list */
  search: string = '';

  /** The currently selected semester */
  semester = 1;

  /** Courses available for the selected semester */
  courses: Course[] = [];

  /** Set of selected course ids */
  selected = new Set<number>();

  /** Error message shown in the UI when something goes wrong */
  error = '';

  /** Informational message shown in the UI on success */
  message = '';

  ngOnInit() {
    this.loadSemester();
  }

  /** Load the courses for the currently selected semester. */
  private loadSemester() {
    this.courses = COURSES_BY_SEMESTER[this.semester] ?? [];
  }

  /**
   * Toggle selection of a course id.
   * @param courseCode id of the course
   * @param checked whether the checkbox is checked
   */
  toggleCourse(courseCode: number, checked: boolean) {
    if (checked) {
      this.selected.add(courseCode);
    } else {
      this.selected.delete(courseCode);
    }
  }

  /** Registration stored for the current student/semester (if any). */
  get registered() {
    return CourseRegistrationStore.get(this.regNumber, this.semester);
  }

  /** Returns the list of Course objects that the student has registered for. */
  get registeredCourses(): Course[] {
    const reg = this.registered;
    if (!reg) {
      return [];
    }
    const set = new Set(reg.courseIds);
    return this.courses.filter((course: Course) => set.has(course.id));
  }

  /**
   * Validate current selection and persist the registration via the store.
   * Validation rules in this demo:
   * - student cannot register twice for the same semester
   * - must select at least 4 courses
   * - all selected ids must be offered this semester
   */
  registerSelected() {
    this.message = '';
    this.error = '';
    try {
      // Check if already registered
      if (CourseRegistrationStore.has(this.regNumber, this.semester)) {
        throw new Error('You have already registered for this semester.');
      }
      // validate minimum 4 courses
      const ids = Array.from(this.selected);
      const uniqueID = Array.from(new Set(ids));
      if (this.selected.size < 4) {
        throw new Error('You must select at least 4 courses.');
      }
      // validate id no duplicate
      const offeredIds = new Set(this.courses.map(c => c.id));
      const invalid = uniqueID.find(id => !offeredIds.has(id));
      if (invalid !== undefined) {
        throw new Error(`Invalid course ID selected: ${invalid}`);
      }
      CourseRegistrationStore.save({
        studentRegNumber: this.regNumber,
        semester: this.semester,
        courseIds: uniqueID,
        createdAt: new Date().toISOString(),
      });
      this.message = 'Courses registered successfully.';
      this.selected.clear();
    } catch (err: any) {
      this.error = err.message;
      return;
    }
  }

  /** Clear stored registration for the current student/semester. */
  clearRegistration() {
    CourseRegistrationStore.clear(this.regNumber, this.semester);
    this.message = 'Registration cleared (local only).';
    this.error = '';
  }

  /** Filter the course list according to the `search` query. */
  get filteredCourses(): Course[] {
    const q = this.search.trim().toLowerCase();

    return this.courses.filter((course: Course) =>
      course.courseCode.toLowerCase().includes(q) ||
      course.title.toLowerCase().includes(q) ||
      course.teachers.some((teacher: string) =>
        teacher.toLowerCase().includes(q)
      )
    );
  }

  /** Placeholder for adding a new course via UI - currently logs to console. */
  onAddCourse() {
    console.log('Add Course button clicked');
  }
}
