import { Component, signal } from '@angular/core';
import { Router , RouterOutlet ,RouterLink, RouterLinkActive} from '@angular/router';
import { AuthSessionService } from './pages/login/admin.service';
import { AuthUser } from './pages/login/login.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule,RouterOutlet , RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {

   constructor(
    private authSession: AuthSessionService,
    private router: Router
  ) {}

  /**
   * Returns the currently logged-in user from localStorage.
   * We use a getter so the navbar can update after login/logout without manually passing data.
   */
  get currentUser(): AuthUser | null {
    return this.authSession.getUser();
  }

  /**
   * Returns true when a token exists in localStorage.
   * This controls which navigation links should be displayed.
   */
  get isLoggedIn(): boolean {
    return this.authSession.isLoggedIn();
  }

  /**
   * Logs the user out by clearing the saved session.
   * After logout, the user is redirected to the login page.
   */
  logout(): void {
    this.authSession.clearSession();
    this.router.navigate(['/login']);
  }
}
