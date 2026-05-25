fiuimport { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course } from './courses.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CoursesApiService {
  constructor(private http: HttpClient) {}

  getCoursesBySemester(semester: number): Observable<Course[]> {
    return this.http.get<Course[]>('/api/courses', { params: { semester } });
  }
}
