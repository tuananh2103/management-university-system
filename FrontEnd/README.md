# 🎓 University Management System – Frontend (Angular)

## 1. Overview

This repository contains the **Frontend** of the *University Management System*, built with **Angular (v17+)** using **Standalone Components**.

The frontend is responsible for:
- Rendering the user interface (UI)
- Handling navigation (routing)
- Managing forms and validations
- Preparing integration with a Java backend (Spring Boot – next phase)

---

## 2. Tech Stack

- **Angular 17+**
- **TypeScript (strict mode enabled)**
- **Standalone Components architecture**
- **SCSS**
- **Angular Router**
- **Reactive Forms & Template-driven Forms**

---

## 3. Project Structure
├── app/
│ ├── pages/
│ │ ├── home/ # Main dashboard
│ │ ├── students/ # Students management
│ │ ├── courses/ # Courses management
│ │ ├── library/ # Library management
│ │ ├── cafe/ # Campus café
│ │ └── login/ # Authentication
│ │
│ ├── app.routes.ts # Application routes
│ ├── app.ts # Root component
│ ├── app.html # Main layout (navbar + router-outlet)
│ └── app.scss # Global app styles
│
├── styles.scss # Global styles
└── main.ts # Application bootstrap


---

## 4. Why Standalone Components?

### What are Standalone Components?

Standalone Components are an Angular architecture (introduced in Angular 14+) that removes the need for `NgModule`.

### Why this approach?
- Cleaner and more readable code
- Better separation of concerns
- Easier maintenance and scalability
- Official **Angular recommended architecture**
- Well-suited for **microservices-based backends**

Example:

```ts
@Component({
  standalone: true,
  imports: [CommonModule],
})
export class StudentsComponent {}

## 5. Why Using that Components?

import { CommonModule } from '@angular/common'; 
//required when using *ngIf,*ngFor,[ngClass] and common pipes(date,uppercase,etc)
import { FormsModule } from '@angular/forms'; 
//Used for template-driven forms, such as:Search input, simple filters , Lightweight forms
import { ReactiveFormsModule } from '@angular/forms';
//Used for critical forms: Login, Registration, Forms with validation rules, Backend-bound forms
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
//Used of page navigation,SPA behavior,Layout rendering via <router-oulet>


## 6. Routing Configuration

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'students', component: StudentsComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'library', component: LibraryComponent },
  { path: 'cafe', component: CafeComponent },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' },
];
Each route maps directly to a Standalone Component.

## 7. Models (Interfaces) // file .model.ts
Why models are important: Strong typing, Easier backend integration, Prevent runtime errors, Cleaner and self-documented code

Attention How to Run the Project
npm install
ng serve -o