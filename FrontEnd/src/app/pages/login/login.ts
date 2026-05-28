import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { AuthApiService } from './admin.api.service';
import { AuthSessionService } from './admin.service';
import { AuthUser, LoginRequest } from './login.model';

// login component is public, but after login, user can access private routes, 
// so we need authGuard to protect those routes. 
// We also need to redirect user to login page ( student for example) if 
// they try to access private routes without being authenticated. 
// After login, we can redirect user back to the original page they wanted to access( here is student).
import { ActivatedRoute, Router } from '@angular/router';
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
  returnUrl = '/';
  loading = false;
  error = '';
  message = '';

  currentUser: AuthUser | null = null;

  constructor(
    private authApi: AuthApiService,
    private authSession: AuthSessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authSession.getUser();

    /**
    * Read the returnUrl from the route query parameters.
    * This is where the user should be redirected after successful login.
    */
    this.returnUrl = this.route.snapshot.queryParamMap.get('returnUrl') || '/';
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
        // After login, redirect user to the original page they wanted to access.
        this.router.navigateByUrl(this.returnUrl);
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