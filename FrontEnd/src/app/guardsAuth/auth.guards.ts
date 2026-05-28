import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { AuthSessionService } from '../pages/login/admin.service';

/**
 * Auth Guard used to protect private routes.
 *
 * If the user is logged in, Angular allows navigation to the requested page.
 * If the user is not logged in, Angular redirects the user to the Login page.
 */
export const authGuard: CanActivateFn = (route, state) => {
  const authSession = inject(AuthSessionService);
  const router = inject(Router);

  /**
   * If a valid demo token exists in localStorage,
   * the user can access the protected route.
   */
  if (authSession.isLoggedIn()) {
    return true;
  }

  /**
   * If the user is not authenticated, redirect to Login.
   * The returnUrl stores the original page the user wanted to access.
   * After login, we can redirect the user back to that page.
   */
  return router.createUrlTree(['/login'], {
    queryParams: {
      returnUrl: state.url,
    },
  });
};