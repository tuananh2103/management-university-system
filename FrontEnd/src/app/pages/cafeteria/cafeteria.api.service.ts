import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CafeteriaItem, CreateCafeteriaItem, UpdateCafeteriaItem } from './cafeteria.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CafeteriaApiService {
  private readonly apiUrl = `${environment.apiUrl}/cafeteria/items`;

  constructor(private http: HttpClient) {}

  getItems(): Observable<CafeteriaItem[]> {
    return this.http.get<CafeteriaItem[]>(this.apiUrl);
  }

  getItemById(id: number): Observable<CafeteriaItem> {
    return this.http.get<CafeteriaItem>(`${this.apiUrl}/${id}`);
  }

  createItem(payload: CreateCafeteriaItem): Observable<CafeteriaItem> {
    return this.http.post<CafeteriaItem>(this.apiUrl, payload);
  }

  updateItem(id: number, payload: UpdateCafeteriaItem): Observable<CafeteriaItem> {
    return this.http.put<CafeteriaItem>(`${this.apiUrl}/${id}`, payload);
  }

  deleteItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
