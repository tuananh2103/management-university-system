import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  CourseRegistration,
  RegisterCoursesRequest,
} from './courses.model';

@Injectable({
  providedIn: 'root',
})
export class RegistrationsApiService {
  private readonly apiUrl = 'http://localhost:8080/api/registrations';

  constructor(private http: HttpClient) {}

  getRegistration(regNumber: string, semester: number): Observable<CourseRegistration> {
    return this.http.get<CourseRegistration>(`${this.apiUrl}/${regNumber}`, {
      params: {
        semester: semester.toString(),
      },
    });
  }

  registerCourses(request: RegisterCoursesRequest): Observable<CourseRegistration> {
    return this.http.post<CourseRegistration>(this.apiUrl, request);
  }

  deleteRegistration(regNumber: string, semester: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${regNumber}`, {
      params: {
        semester: semester.toString(),
      },
    });
  }
}