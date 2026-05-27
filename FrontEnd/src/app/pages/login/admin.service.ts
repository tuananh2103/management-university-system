import { Injectable } from '@angular/core';
import { AuthUser, LoginResponse } from './login.model';

@Injectable({
  providedIn: 'root',
})
export class AuthSessionService {
  private readonly tokenKey = 'university_auth_token';
  private readonly userKey = 'university_auth_user';

  saveSession(response: LoginResponse): void {
    localStorage.setItem(this.tokenKey, response.token);
    localStorage.setItem(this.userKey, JSON.stringify(response.user));
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUser(): AuthUser | null {
    const rawUser = localStorage.getItem(this.userKey);

    if (!rawUser) {
      return null;
    }

    return JSON.parse(rawUser) as AuthUser;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  clearSession(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }
}