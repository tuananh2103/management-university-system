import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home';
import { StudentsComponent } from './pages/students/students';
import { CoursesComponent } from './pages/courses/courses';
import { LoginComponent } from './pages/login/login';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'students', component: StudentsComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'login', component: LoginComponent },
];
