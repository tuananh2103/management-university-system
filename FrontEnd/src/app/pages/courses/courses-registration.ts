import { Injectable } from '@angular/core';
import { CourseRegistration } from './courses-registration.store';

/**
 * Service that provides a thin localStorage-backed API for storing
 * and retrieving a student's course registration payload.
 *
 * This service is intentionally small and synchronous since the data is
 * stored in `localStorage`. It performs basic validation (semester range
 * and minimum courses) when registering.
 */
@Injectable({ providedIn: 'root' })
export class CourseRegistrationService {
  /**
   * Create the storage key for a registration entry.
   * @param regNumber student registration number (e.g. SP21-BCS-066)
   */
  private key(regNumber: string) {
    return `registrations:${regNumber}`;
  }

  /**
   * Returns true if this student has any stored registration.
   * @param regNumber student registration number
   */
  hasRegistration(regNumber: string): boolean {
    return localStorage.getItem(this.key(regNumber)) !== null;
  }

  /**
   * Retrieve a previously stored registration payload, or null if none.
   * @param regNumber student registration number
   */
  getRegistration(regNumber: string): CourseRegistration | null {
    const raw = localStorage.getItem(this.key(regNumber));
    return raw ? (JSON.parse(raw) as CourseRegistration) : null;
  }

  /**
   * Validate and save a registration for a student.
   * - Ensures semester is in range 1..8
   * - Deduplicates courseIds and enforces a minimum of 4 courses
   * @returns the saved CourseRegistration object
   */
  register(regNumber: string, semester: number, courseIds: number[]): CourseRegistration {
    if (semester < 1 || semester > 8) {
      throw new Error('Invalid semester. Must be between 1 and 8.');
    }

    const unique = Array.from(new Set(courseIds));
    if (unique.length < 4) {
      throw new Error('You must register at least 4 courses.');
    }

    const payload: CourseRegistration = {
      studentRegNumber: regNumber,
      semester,
      courseIds: unique,
      createdAt: new Date().toISOString(),
    };

    localStorage.setItem(this.key(regNumber), JSON.stringify(payload));
    return payload;
  }

  /**
   * Remove a student's registration (local only).
   * @param regNumber student registration number
   */
  clear(regNumber: string): void {
    localStorage.removeItem(this.key(regNumber));
  }
}
