import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { AuthApiService } from './admin.api.service';
import { AuthSessionService } from './admin.service';
import { AuthUser, LoginRequest } from './login.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class LoginComponent implements OnInit {
  form: LoginRequest = {
    username: '',
    password: '',
  };

  loading = false;
  error = '';
  message = '';

  currentUser: AuthUser | null = null;

  constructor(
    private authApi: AuthApiService,
    private authSession: AuthSessionService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authSession.getUser();
  }

  login(): void {
    this.error = '';
    this.message = '';

    if (!this.form.username.trim()) {
      this.error = 'Username is required.';
      return;
    }

    if (!this.form.password.trim()) {
      this.error = 'Password is required.';
      return;
    }

    this.loading = true;

    this.authApi.login(this.form).subscribe({
      next: (response) => {
        this.authSession.saveSession(response);
        this.currentUser = response.user;
        this.message = 'Login successful.';
        this.loading = false;

        this.form = {
          username: '',
          password: '',
        };
      },
      error: (err: HttpErrorResponse) => {
        this.error =
          err.error?.message ||
          err.error?.detail ||
          'Invalid username or password.';
        this.loading = false;
      },
    });
  }

  logout(): void {
    this.authSession.clearSession();
    this.currentUser = null;
    this.message = 'Logged out successfully.';
    this.error = '';
  }
}