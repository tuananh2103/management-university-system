import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardSummary } from './home.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class HomeApiService {
  private readonly apiUrl = `${environment.apiUrl}/dashboard`;

  constructor(private http: HttpClient) {}

  getDashboardSummary(): Observable<DashboardSummary> {
    return this.http.get<DashboardSummary>(`${this.apiUrl}/summary`);
  }
}
