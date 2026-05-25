import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course } from './courses.model';
import { Observable } from 'rxjs';
//call API
@Injectable({ providedIn: 'root' })

export class CoursesApiService {
  private readonly apiUrl = 'http://localhost:8080/api/courses';
  constructor(private http: HttpClient) {}

  getCoursesBySemester(semester: number): Observable<Course[]> {
    return this.http.get<Course[]>(this.apiUrl, {
      params: {
        semester: semester.toString(),
      },
    });
  }
}
