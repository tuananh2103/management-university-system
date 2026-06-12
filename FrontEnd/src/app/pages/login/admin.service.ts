import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthUser, LoginResponse } from './login.model';

@Injectable({
  providedIn: 'root',
})
export class AuthSessionService {
  private readonly platformId = inject(PLATFORM_ID);
  private readonly tokenKey = 'university_auth_token';
  private readonly userKey = 'university_auth_user';

  saveSession(response: LoginResponse): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.tokenKey, response.token);
      localStorage.setItem(this.userKey, JSON.stringify(response.user));
    }
  }

  getToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) return null;
    return localStorage.getItem(this.tokenKey);
  }

  getUser(): AuthUser | null {
    if (!isPlatformBrowser(this.platformId)) return null;
    const rawUser = localStorage.getItem(this.userKey);
    if (!rawUser) return null;
    return JSON.parse(rawUser) as AuthUser;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  clearSession(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.userKey);
    }
  }
}
