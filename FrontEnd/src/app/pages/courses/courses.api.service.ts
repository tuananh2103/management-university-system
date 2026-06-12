import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Course } from './courses.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CoursesApiService {
  private readonly apiUrl = `${environment.apiUrl}/courses`;

  constructor(private http: HttpClient) {}

  getCoursesBySemester(semester: number): Observable<Course[]> {
    return this.http.get<Course[]>(this.apiUrl, {
      params: { semester: semester.toString() }
    });
  }
}
