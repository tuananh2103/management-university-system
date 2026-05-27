import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CreateStudent, Student, UpdateStudent } from './students.model';

@Injectable({
  providedIn: 'root',
})
export class StudentsApiService {
  private readonly apiUrl = 'http://localhost:8080/api/students';

  constructor(private http: HttpClient) {}

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.apiUrl);
  }

  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.apiUrl}/${id}`);
  }

  createStudent(payload: CreateStudent): Observable<Student> {
    return this.http.post<Student>(this.apiUrl, payload);
  }

  updateStudent(id: number, payload: UpdateStudent): Observable<Student> {
    return this.http.put<Student>(`${this.apiUrl}/${id}`, payload);
  }

  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}