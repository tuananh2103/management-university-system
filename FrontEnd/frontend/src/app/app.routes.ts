import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home';
import { StudentsComponent } from './pages/students/students';
import { CoursesComponent } from './pages/courses/courses';
import { LoginComponent } from './pages/login/login';
import { CafeteriaComponent } from './pages/cafeteria/cafeteria';
import { LibraryComponent } from './pages/library/library';

export const routes: Routes = [
  { path: '', component: HomeComponent },          // Main page
  { path: 'students', component: StudentsComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'login', component: LoginComponent },
  { path: 'Restaurant', component: CafeteriaComponent },
  { path: 'Library', component: LibraryComponent },
  // route n'exist pas -> return home
  { path: '**', redirectTo: '' },
];
