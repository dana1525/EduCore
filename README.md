# EduCore

EduCore is a console-based e-learning platform where instructors can create courses with modules and quizzes, and students can enroll, track their progress, and receive certificates upon completion.

Built as a university project for the Advanced Object-Oriented Programming course, structured in two stages — Stage I uses in-memory collections, Stage II adds PostgreSQL persistence via JDBC.

## Requirements

- Java 21+
- Maven 3.8+
- PostgreSQL 14+

## How to Run

1. Create the database and run the schema:
```bash
psql -U postgres -c "CREATE DATABASE educore;"
psql -U postgres -d educore -f schema.sql
```

2. Configure your credentials in a `.env` file in the project root:
```
DB_URL=jdbc:postgresql://localhost:5432/educore
DB_USER=your_user
DB_PASSWORD=your_password
```

3. Build and run:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.elearning.Main"
```

## Project Structure

```
src/main/java/com/elearning/
├── config/       ← DatabaseConnection Singleton
├── model/        ← entity classes
├── enums/        ← Difficulty, EnrollmentStatus, Specialty
├── repository/   ← JDBC data access (GenericRepository + concrete repos)
├── service/      ← business logic + AuditService
└── Main.java
```

## Architecture

The application is structured in four layers:

```
Main → Service → Repository → PostgreSQL
```

**Main** handles user input and output only — it calls services and displays results.

**Service layer** contains all business logic — validation, rules, orchestration between entities. Services never contain SQL.

**Repository layer** contains all database access — each entity has a repository that extends `GenericRepository<T>`, an abstract class that defines the CRUD contract and provides shared helper methods (`executeInsert`, `executeUpdate`).

**`DatabaseConnection`** is a Singleton — a single shared JDBC connection used across all repositories.

**`AuditService`** is a Singleton — a single writer that appends every system action to `audit.csv`.

### Inheritance

```
User (abstract)
├── Student    — has progress (0–100%)
└── Instructor — has specialty
```

`User` is abstract because only concrete types exist in the system. The abstract method `getRole()` forces each subclass to identify itself and ensures the correct role value is written to the database on insert.

### Enums

| Enum | Values |
|---|---|
| `Difficulty` | `BEGINNER`, `INTERMEDIATE`, `ADVANCED` |
| `EnrollmentStatus` | `ACTIVE`, `COMPLETED`, `CANCELLED` |
| `Specialty` | `JAVA`, `PYTHON`, `DATABASE`, `WEB_DEVELOPMENT`, `DATA_SCIENCE`, `UI_UX` |

## System Actions

**Course Management**
1. Register an instructor
2. Add a course
3. Add a module to a course
4. Create a quiz for a module
5. Add a question to a quiz

**Student Management**
6. Register a student
7. Enroll a student in a course
8. Complete a quiz
9. Update student progress
10. Complete an enrollment
11. Cancel an enrollment
12. Issue a certificate

**Reports**
13. List all courses
14. List all students
15. List all instructors
16. List enrollments by student
17. List students by course
18. List certificates by student
19. Get best quiz score for a student
20. List quiz results by student
21. Find instructors by specialty

## Audit

Every system action is logged to `audit.csv` in the project root:

```
register_instructor, 2025-06-01T14:32:10.123
add_course, 2025-06-01T14:33:05.456
enroll_student, 2025-06-01T14:35:22.789
```

## Technologies

| Component | Technology |
|---|---|
| Language | Java 21 |
| Build tool | Maven |
| Database | PostgreSQL 14 |
| DB access | JDBC |
| Configuration | dotenv-java |
| Audit | CSV via BufferedWriter |

## Features

The CLI exposes 21 actions split into three categories:

**Course & Content Management**

- Register an instructor with a domain specialty
- Create a course bound to a difficulty level and instructor
- Add ordered modules to a course
- Attach quizzes to modules
- Add 4-option multiple choice questions to quizzes

**Student Lifecycle**

- Register students
- Enroll a student in a course (status starts as `ACTIVE`)
- Complete a quiz — answers are matched by option index
- Update student progress based on best quiz score
- Complete an enrollment when the student passes (status → `COMPLETED`)
- Cancel an active enrollment (status → `CANCELLED`)
- Issue a certificate when a course is completed

**Reports**

- List all courses, students, or instructors
- Filter enrollments, certificates, and quiz results by student
- Cross-reference students enrolled in a specific course
- Find instructors by specialty
- Look up a student's best score for a specific quiz