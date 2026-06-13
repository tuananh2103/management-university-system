# University Management System

A full-stack web application for managing university operations — students, courses, library, cafeteria — built with **Spring Boot 4** on the backend and **Angular 20** on the frontend, backed by **PostgreSQL**.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Features](#features)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Authentication & Security](#authentication--security)
- [Getting Started](#getting-started)
- [CI/CD & Deployment](#cicd--deployment)
- [Default Accounts](#default-accounts)

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 25, Spring Boot 4.0.1, Spring Security, Spring Data JPA |
| **Frontend** | Angular 20.3, TailwindCSS 4, Angular SSR |
| **Database** | PostgreSQL 16 |
| **Auth** | JWT (jjwt 0.12.6) + BCrypt |
| **Infra** | Docker Compose, Railway, GitHub Actions |

---

## Features

### Dashboard
- Overview stats: total students (active/inactive), books (available/borrowed), cafeteria items (available/sold out), 8 semesters of courses
- Shows logged-in user info (name + role)
- Quick-access links to all modules

### Students
- Full CRUD: create, read, update, delete
- Fields: student code (unique), full name, email, major, year (1–4), status (ACTIVE / INACTIVE)
- Client-side search by name, code, or email
- Inline add/edit form + refresh button

### Courses
- Browse courses by semester (Semester 1–8)
- Course data seeded from txt files at startup (course code, title, credits, lecture hours, lab hours, teachers)
- Course registration: enter a reg number, select semester, pick ≥ 4 courses, register
- View registered courses for a student per semester
- Delete/clear a registration

### Library
- Full CRUD for books
- Fields: ISBN (unique), title, author, category, published year, status (AVAILABLE / BORROWED)

### Cafeteria
- Full CRUD for menu items
- Fields: name, category, price (decimal), description, status (AVAILABLE / SOLD_OUT)

### Authentication
- Login with username + password → returns JWT token
- Token stored in `localStorage`, auto-attached to all API calls via HTTP interceptor
- Route guards protect `/students`, `/courses`, `/library`, `/cafeteria`
- Home page (`/`) and login page (`/login`) are public

---

## Project Structure

```
Management-University/
├── BackEnd/                        # Spring Boot application
│   ├── src/main/java/university/management/
│   │   ├── admin/                  # Auth, JWT, Spring Security config
│   │   │   ├── controller/         # AuthController  (POST /api/auth/login)
│   │   │   ├── entity/             # AdminUser
│   │   │   ├── repository/         # AdminUserRepository
│   │   │   ├── security/           # JwtService, JwtAuthenticationFilter, SecurityConfig
│   │   │   └── service/            # AuthService
│   │   ├── students/               # Student CRUD
│   │   ├── courses/                # Courses + CourseRegistration
│   │   ├── library/                # Books CRUD
│   │   ├── cafe/                   # Cafeteria items CRUD
│   │   ├── dashboard/              # Aggregated summary stats
│   │   ├── transport/              # TMS stub (no REST yet)
│   │   └── common/                 # WebConfig (CORS), DataSeeder, TermsAndConditions
│   └── src/main/resources/
│       ├── application.properties  # DB + JWT + CORS config via env vars
│       └── courses/1.txt … 8.txt   # Course seed data per semester
├── FrontEnd/                       # Angular application
│   └── src/app/
│       ├── pages/
│       │   ├── home/               # Dashboard
│       │   ├── login/              # Login form + AuthSessionService
│       │   ├── students/           # Student CRUD table
│       │   ├── courses/            # Course listing + registration
│       │   ├── library/            # Books management
│       │   └── cafeteria/          # Cafeteria items
│       ├── guardsAuth/             # authGuard (route protection)
│       └── interceptors/           # authInterceptor (auto JWT header)
├── Postgres/                       # Local Postgres Docker config + .env
├── docker-compose.yml              # Local dev: Postgres + Backend
└── .github/workflows/
    ├── ci.yml                      # CI: backend tests + frontend build
    └── deploy.yml                  # CD: deploy to Railway on push to main
```

---

## Architecture

```
Browser (Angular 20)
    │
    │  HTTP + Bearer JWT
    ▼
Spring Boot 4 (port 8080)
    │   Spring Security filter chain
    │   JwtAuthenticationFilter
    │
    ├── /api/auth/*         (public)
    ├── /api/students/*     (protected)
    ├── /api/courses/*      (protected)
    ├── /api/registrations/* (protected)
    ├── /api/books/*        (protected)
    ├── /api/cafeteria/*    (protected)
    └── /api/dashboard/*    (protected)
    │
    ▼
PostgreSQL 16 (port 5432)
    university_db
```

**Startup:** `DataSeeder` runs on boot and seeds admin users, students, books, cafeteria items, and all 8 semesters of courses — only if the tables are empty.

---

## API Endpoints

### Auth
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/login` | Public | Login → returns JWT token + user info |

### Students
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | List all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |

### Courses
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/courses?semester={1-8}` | List courses for a semester |

### Course Registrations
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/registrations` | Register courses for a student |
| GET | `/api/registrations/{regNumber}?semester={n}` | Get registration |
| DELETE | `/api/registrations/{regNumber}?semester={n}` | Delete registration |

### Library
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/books` | List all books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Add book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |

### Cafeteria
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/cafeteria` | List all items |
| GET | `/api/cafeteria/{id}` | Get item by ID |
| POST | `/api/cafeteria` | Add item |
| PUT | `/api/cafeteria/{id}` | Update item |
| DELETE | `/api/cafeteria/{id}` | Delete item |

### Dashboard
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/dashboard/summary` | Returns aggregate stats for all modules |

---

## Database Schema

### `admin_users`
| Column | Type | Notes |
|---|---|---|
| id | BIGINT PK | Auto-increment |
| username | VARCHAR(50) | Unique |
| password | VARCHAR(255) | BCrypt hashed |
| full_name | VARCHAR(100) | |
| role | VARCHAR(20) | ADMIN / STUDENT |

### `students`
| Column | Type | Notes |
|---|---|---|
| id | BIGINT PK | |
| student_code | VARCHAR(20) | Unique |
| full_name | VARCHAR(100) | |
| email | VARCHAR(150) | Unique |
| major | VARCHAR(100) | |
| year | INT | |
| status | VARCHAR(20) | ACTIVE / INACTIVE |
| created_at | TIMESTAMP | Set on insert |

### `courses`
| Column | Type | Notes |
|---|---|---|
| id | BIGINT PK | |
| course_code | VARCHAR(20) | Unique |
| title | VARCHAR(200) | |
| credits | INT | |
| lecture_hours | INT | |
| lab_hours | INT | |
| teachers | TEXT | |
| semester | INT | 1–8 |
| created_at | TIMESTAMP | |

### `books`
| Column | Type | Notes |
|---|---|---|
| id | BIGINT PK | |
| isbn | VARCHAR(20) | Unique |
| title | VARCHAR(200) | |
| author | VARCHAR(100) | |
| category | VARCHAR(50) | |
| published_year | INT | |
| status | VARCHAR(20) | AVAILABLE / BORROWED |
| created_at | TIMESTAMP | |

### `cafeteria_items`
| Column | Type | Notes |
|---|---|---|
| id | BIGINT PK | |
| name | VARCHAR(100) | |
| category | VARCHAR(50) | |
| price | DECIMAL(10,2) | |
| description | TEXT | |
| status | VARCHAR(20) | AVAILABLE / SOLD_OUT |
| created_at | TIMESTAMP | |

---

## Authentication & Security

- **Spring Security** with stateless session (no server-side sessions)
- **JWT** signed with HMAC-SHA, configurable secret + expiration (default: 24h)
- Only `POST /api/auth/login` and `OPTIONS /**` are public; all other endpoints require a valid token
- **CORS** configured for `http://localhost:4200` and `FRONTEND_URL` env var
- Frontend stores token as `university_auth_token` in `localStorage`
- `authInterceptor` clones every request and injects `Authorization: Bearer <token>`
- `authGuard` redirects unauthenticated users to `/login`

---

## Getting Started

### Prerequisites
- Java 25
- Node.js 22
- Docker + Docker Compose

### Option 1 — Docker Compose (Postgres + Backend only)

```bash
docker-compose up -d
```

Then run the frontend separately:

```bash
cd FrontEnd
npm install
npm start
```

Open [http://localhost:4200](http://localhost:4200).

### Option 2 — Manual

**Backend:**

```bash
cd BackEnd
# Set env vars or use defaults (see application.properties)
./mvnw spring-boot:run
```

**Frontend:**

```bash
cd FrontEnd
npm install
npm start
```

### Environment Variables (Backend)

| Variable | Default | Description |
|---|---|---|
| `JDBC_DATABASE_URL` | `jdbc:postgresql://localhost:5432/university_db` | PostgreSQL URL |
| `DATABASE_USER` | `postgres` | DB username |
| `DATABASE_PASSWORD` | `postgres` | DB password |
| `JWT_SECRET` | `dev_secret_min_32_chars_xxxxxxxxxxxxxxxxx` | JWT signing key (min 32 chars) |
| `FRONTEND_URL` | `http://localhost:4200` | CORS allowed origin |

---

## CI/CD & Deployment

### GitHub Actions

**CI (`ci.yml`)** — triggers on push to `main` or `develop`, and on PRs to `main`:
- Spins up a Postgres 16 service container
- Runs `./mvnw test` against it (backend)
- Runs `npm ci && npm run build` (frontend)

**Deploy (`deploy.yml`)** — triggers on push to `main`:
- Builds the Spring Boot JAR and deploys to Railway (`backend` service)
- Builds the Angular app and deploys to Railway (`frontend` service)
- Frontend deploy waits for backend to finish first

### Deployment target: [Railway](https://railway.app)
Configure `RAILWAY_TOKEN` in GitHub repository secrets.

---

## Default Accounts

Seeded automatically on first startup:

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `student` | `student123` | STUDENT |

> Passwords are BCrypt-hashed in the database. Change these before any production deployment.
