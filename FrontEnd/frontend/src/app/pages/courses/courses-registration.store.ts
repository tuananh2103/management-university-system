
/**
 * Payload stored for a student's course registration.
 */
export interface CourseRegistration {
    /** Student registration number (unique identifier for the student) */
    studentRegNumber: string;
    /** Semester number the registration belongs to (1..8) */
    semester: number;
    /** Array of selected course ids */
    courseIds: number[];
    /** ISO timestamp of when the registration was created */
    createdAt: string;
}

/**
 * Lightweight helper for interacting with localStorage for a specific
 * semester/registration pair. All methods are static so it can be used
 * without instantiating the class.
 */
export class CourseRegistrationStore {
    private static key(regNumber: string, semester: number) {
        return `registration:${regNumber}:semester:${semester}`;
    }

    /**
     * Retrieve a stored registration or null if not present.
     */
    static get(regNumber: string, semester: number): CourseRegistration | null {
        const key = this.key(regNumber, semester);
        const raw = localStorage.getItem(key);
        return raw ? (JSON.parse(raw) as CourseRegistration) : null;
    }

    /**
     * Returns true when a registration exists for the provided student/semester.
     */
    static has(regNumber: string, semester: number): boolean {
        return this.get(regNumber, semester) !== null;
    }

    /**
     * Save or overwrite a registration payload.
     */
    static save(registration: CourseRegistration): void {
        localStorage.setItem(this.key(registration.studentRegNumber, registration.semester), JSON.stringify(registration));
    }

    /**
     * Remove a stored registration (local only).
     */
    static clear(regNumber: string, semester: number): void {
        localStorage.removeItem(this.key(regNumber, semester));
    }
}