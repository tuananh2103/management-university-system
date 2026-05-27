import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import {
  CreateStudent,
  Student,
  StudentStatus,
  UpdateStudent,
} from './students.model';

import { StudentsApiService } from './students.api.service';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './students.html',
  styleUrl: './students.scss',
})
export class StudentsComponent implements OnInit {
  students: Student[] = [];

  search = '';

  loading = false;
  saving = false;

  error = '';
  message = '';

  editingId: number | null = null;

  form: CreateStudent = {
    studentCode: '',
    fullName: '',
    email: '',
    major: 'Computer Science',
    year: 1,
    status: 'ACTIVE',
  };

  statuses: StudentStatus[] = ['ACTIVE', 'INACTIVE'];
  years = [1, 2, 3, 4, 5];

  constructor(private studentsApi: StudentsApiService) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(clearMessages = true): void {
    this.loading = true;
      if (clearMessages) {
    this.error = '';
    this.message = '';
   }

    this.studentsApi.getStudents().subscribe({
      next: (data) => {
        this.students = [...data];
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load students from backend.';
        this.students = [];
        this.loading = false;
      },
    });
  }

  submitForm(): void {
    this.error = '';
    this.message = '';

    if (!this.form.studentCode.trim()) {
      this.error = 'Student code is required.';
      return;
    }

    if (!this.form.fullName.trim()) {
      this.error = 'Full name is required.';
      return;
    }

    if (!this.form.email.trim()) {
      this.error = 'Email is required.';
      return;
    }

    this.saving = true;

    if (this.editingId === null) {
      this.createStudent();
    } else {
      this.updateStudent(this.editingId);
    }
  }

  createStudent(): void {
  this.studentsApi.createStudent(this.form).subscribe({
    next: () => {
      this.message = 'Student created successfully.';
      this.saving = false;
      this.resetForm();

      // Reload list from backend immediately
      this.loadStudents(false);
    },
    error: (err: HttpErrorResponse) => {
      this.error =
        err.error?.message ||
        err.error?.detail ||
        'Failed to create student.';
      this.saving = false;
    },
  });
}

  updateStudent(id: number): void {
  const payload: UpdateStudent = { ...this.form };

  this.studentsApi.updateStudent(id, payload).subscribe({
    next: () => {
      this.message = 'Student updated successfully.';
      this.saving = false;
      this.resetForm();

      // Reload list from backend immediately
      this.loadStudents(false);
    },
    error: (err: HttpErrorResponse) => {
      this.error =
        err.error?.message ||
        err.error?.detail ||
        'Failed to update student.';
      this.saving = false;
    },
  });
}

  startEdit(student: Student): void {
    this.error = '';
    this.message = '';
    this.editingId = student.id;

    this.form = {
      studentCode: student.studentCode,
      fullName: student.fullName,
      email: student.email,
      major: student.major,
      year: student.year,
      status: student.status,
    };
  }

  cancelEdit(): void {
    this.resetForm();
  }

  deleteStudent(id: number): void {
  this.error = '';
  this.message = '';

  this.studentsApi.deleteStudent(id).subscribe({
    next: () => {
      this.message = 'Student deleted successfully.';

      if (this.editingId === id) {
        this.resetForm();
      }

      // Reload list from backend immediately
      this.loadStudents(false);
    },
    error: () => {
      this.error = 'Failed to delete student.';
    },
  });
}

  resetForm(): void {
    this.editingId = null;

    this.form = {
      studentCode: '',
      fullName: '',
      email: '',
      major: 'Computer Science',
      year: 1,
      status: 'ACTIVE',
    };
  }

  get filteredStudents(): Student[] {
    const q = this.search.trim().toLowerCase();

    if (!q) {
      return this.students;
    }

    return this.students.filter((student) => {
      return (
        student.studentCode.toLowerCase().includes(q) ||
        student.fullName.toLowerCase().includes(q) ||
        student.email.toLowerCase().includes(q) ||
        student.major.toLowerCase().includes(q) ||
        student.status.toLowerCase().includes(q)
      );
    });
  }
}