import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs';

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

    /**
     * Basic frontend validation before calling the backend.
     */
    if (!this.form.username.trim()) {
      this.error = 'Username is required.';
      return;
    }

    if (!this.form.password.trim()) {
      this.error = 'Password is required.';
      return;
    }

    this.loading = true;

    /**
     * Send login credentials to the backend.
     * finalize() ensures the loading state is always reset.
     */
    this.authApi
      .login(this.form)
      .pipe(
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (response) => {
          /**
           * Save the token and user information in localStorage.
           * This is what the Auth Guard checks later.
           */
          this.authSession.saveSession(response);
          /**
           * Update the current user state.
           */
          this.currentUser = response.user;
          /**
           * Clear the form after successful login.
           */
          this.form = {
            username: '',
            password: '',
          };
          /**
           * Redirect the user to the page they originally wanted to access.
           * Example: /students
           */
          this.router.navigateByUrl(this.returnUrl);
        },
        error: (err: HttpErrorResponse) => {
          console.error('Login error:', err);

          this.error =
            err.error?.message ||
            err.error?.detail ||
            'Invalid username or password.';
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