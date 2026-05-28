import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home';
import { StudentsComponent } from './pages/students/students';
import { CoursesComponent } from './pages/courses/courses';
import { LoginComponent } from './pages/login/login';
import { CafeteriaComponent } from './pages/cafeteria/cafeteria';
import { LibraryComponent } from './pages/library/library';
import { authGuard } from './guardsAuth/auth.guards';
export const routes: Routes = [
  { path: '', component: HomeComponent },          // Main page
  { path: 'students', component: StudentsComponent, canActivate: [authGuard] },
  { path: 'courses', component: CoursesComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'cafeteria', component: CafeteriaComponent , canActivate: [authGuard]},
  { path: 'library', component: LibraryComponent, canActivate: [authGuard] },
  // route n'exist pas -> return home
  { path: '**', redirectTo: '' },
];
