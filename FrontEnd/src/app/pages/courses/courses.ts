/**
 * Main component for the Courses page. This standalone component shows
 * a list of offered courses for a selected semester and allows the
 * student (identified by `regNumber`) to select and register courses.
 *
 * Notes:
 * - The component uses `CourseRegistrationStore` which persists to
 *   `localStorage` for demo purposes only.
 */

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { Course, CourseRegistration } from './courses.model';
import { CoursesApiService } from './courses.api.service';
import { RegistrationsApiService } from './registrations.api.service';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './courses.html',
  styleUrl: './courses.scss',
})
export class CoursesComponent implements OnInit {
  regNumber = 'SP21-BCS-066';

  semester = 1;
  semesters = [1, 2, 3, 4, 5, 6, 7, 8];

  search = '';

  courses: Course[] = [];
  selectedCourseIds = new Set<number>();

  registration: CourseRegistration | null = null;

  loading = false;
  registrationLoading = false;

  error = '';
  message = '';

  constructor(
    private coursesApi: CoursesApiService,
    private registrationsApi: RegistrationsApiService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadSemester();
  }

  loadSemester(): void {
    this.loading = true;
    this.error = '';
    this.message = '';
    this.registration = null;
    this.selectedCourseIds = new Set<number>();

    this.coursesApi.getCoursesBySemester(this.semester).subscribe({
      next: (data) => {
        this.courses = data;
        this.loading = false;
        this.loadRegistration();
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Failed to load courses from backend.';
        this.courses = [];
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }

  loadRegistration(): void {
    const regNumber = this.regNumber.trim();

    if (!regNumber) {
      this.registration = null;
      return;
    }

    this.registrationLoading = true;

    this.registrationsApi
      .getRegistration(regNumber, this.semester)
      .subscribe({
        next: (registration) => {
          this.registration = registration;
          this.registrationLoading = false;
          this.cdr.detectChanges();
        },
        error: (err: HttpErrorResponse) => {
          if (err.status === 404) {
            this.registration = null;
          } else {
            this.error = 'Failed to load registration.';
          }

          this.registrationLoading = false;
          this.cdr.detectChanges();
        },
      });
  }

  onRegNumberChange(): void {
    this.error = '';
    this.message = '';
    this.selectedCourseIds = new Set<number>();
    this.loadRegistration();
  }

  toggleCourse(courseId: number, checked: boolean): void {
    const nextSelected = new Set(this.selectedCourseIds);

    if (checked) {
      nextSelected.add(courseId);
    } else {
      nextSelected.delete(courseId);
    }

    this.selectedCourseIds = nextSelected;
  }

  isSelected(courseId: number): boolean {
    return this.selectedCourseIds.has(courseId);
  }

  registerSelected(): void {
    this.error = '';
    this.message = '';

    const regNumber = this.regNumber.trim();

    if (!regNumber) {
      this.error = 'Registration number is required.';
      return;
    }

    if (this.registration) {
      this.error = 'This student has already registered courses for this semester.';
      return;
    }

    const courseIds = Array.from(this.selectedCourseIds);

    if (courseIds.length < 4) {
      this.error = 'You must select at least 4 courses.';
      return;
    }

    this.registrationLoading = true;

    this.registrationsApi
      .registerCourses({
        regNumber,
        semester: this.semester,
        courseIds,
      })
      .subscribe({
        next: (registration) => {
          this.registration = registration;
          this.selectedCourseIds = new Set<number>();
          this.message = 'Courses registered successfully.';
          this.registrationLoading = false;
          this.cdr.detectChanges();
        },
        error: (err: HttpErrorResponse) => {
          this.error =
            err.error?.message ||
            err.error?.detail ||
            'Failed to register courses.';

          this.registrationLoading = false;
          this.cdr.detectChanges();
        },
      });
  }

  clearRegistration(): void {
    this.error = '';
    this.message = '';

    const regNumber = this.regNumber.trim();

    if (!regNumber) {
      this.error = 'Registration number is required.';
      return;
    }

    this.registrationLoading = true;

    this.registrationsApi
      .deleteRegistration(regNumber, this.semester)
      .subscribe({
        next: () => {
          this.registration = null;
          this.selectedCourseIds = new Set<number>();
          this.message = 'Registration cleared.';
          this.registrationLoading = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Failed to clear registration.';
          this.registrationLoading = false;
          this.cdr.detectChanges();
        },
      });
  }

  get filteredCourses(): Course[] {
    const q = this.search.trim().toLowerCase();

    if (!q) {
      return this.courses;
    }

    return this.courses.filter((course) => {
      return (
        course.courseCode.toLowerCase().includes(q) ||
        course.title.toLowerCase().includes(q) ||
        course.teachers.join(' ').toLowerCase().includes(q)
      );
    });
  }

  get registeredCourses(): Course[] {
    if (!this.registration) {
      return [];
    }

    const registeredIds = new Set(this.registration.courseIds);

    return this.courses.filter((course) => registeredIds.has(course.id));
  }
}