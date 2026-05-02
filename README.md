# EduCore
## Overview

A Java-based e-learning platform built as a university project for the Advanced Object-Oriented Programming course.

EduCore is a console-based e-learning platform where instructors can create
courses with modules and quizzes, and students can enroll, track their progress,
and receive certificates upon completion.

## Project Structure

```
src/main/java/com/elearning/
├── model/
│   ├── User.java            <- abstract base class
│   ├── Student.java         <- extends User
│   ├── Instructor.java      <- extends User
│   ├── Course.java
│   ├── CourseModule.java
│   ├── Quiz.java
│   ├── QuizResult.java
│   ├── Question.java
│   ├── Enrollment.java
│   └── Certificate.java
├── service/
│   ├── StudentService.java
│   ├── InstructorService.java
│   ├── CourseService.java
│   ├── EnrollmentService.java
│   ├── QuizService.java
│   ├── QuizResultService.java
│   └── CertificateService.java
└── Main.java
```

## System Actions

1. Register a student
2. Register an instructor
3. Add a course
4. Enroll a student in a course
5. Add a module to a course
6. Create a quiz for a module
7. Add a question to a quiz
8. Complete a quiz
9. Update student progress
10. Complete an enrollment
11. Issue a certificate
12. List all courses (sorted alphabetically)
13. List enrollments by student
14. List students by course
15. List certificates by student
16. Get best quiz score for a student

## How to Run

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.elearning.Main"
```

## Stage II — Roadmap

- Add `DatabaseConnection` singleton
- Create repository classes for each entity
- Implement CRUD operations via JDBC
- Add `AuditService` that logs actions to CSV
- Migrate services to use repositories instead of in-memory collections