DROP TABLE IF EXISTS options;
DROP TABLE IF EXISTS quiz_results;
DROP TABLE IF EXISTS certificates;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS modules;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS instructors;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       role VARCHAR(20) NOT NULL -- 'STUDENT' sau 'INSTRUCTOR'
);

CREATE TABLE students (
                          user_id INT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                          progress DOUBLE PRECISION DEFAULT 0.0
);

CREATE TABLE instructors (
                             user_id INT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                             specialty VARCHAR(50) NOT NULL
);

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(200) NOT NULL UNIQUE,
                         description TEXT,
                         difficulty VARCHAR(20) NOT NULL,
                         instructor_id INT NOT NULL REFERENCES instructors(user_id)
);

CREATE TABLE modules (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(200) NOT NULL,
                         position INT NOT NULL,
                         course_id INT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                         UNIQUE(course_id, position)
);

CREATE TABLE quizzes (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(200) NOT NULL,
                         module_id INT NOT NULL REFERENCES modules(id) ON DELETE CASCADE
);

CREATE TABLE questions (
                           id SERIAL PRIMARY KEY,
                           text TEXT NOT NULL,
                           correct_answer VARCHAR(200) NOT NULL,
                           quiz_id INT NOT NULL REFERENCES quizzes(id) ON DELETE CASCADE
);

-- options e un tabel separat
CREATE TABLE options (
                         id SERIAL PRIMARY KEY,
                         text VARCHAR(200) NOT NULL,
                         question_id INT NOT NULL REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE enrollments (
                             id SERIAL PRIMARY KEY,
                             student_id INT NOT NULL REFERENCES students(user_id),
                             course_id INT NOT NULL REFERENCES courses(id),
                             enrollment_date DATE NOT NULL DEFAULT CURRENT_DATE,
                             status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE certificates (
                              id SERIAL PRIMARY KEY,
                              student_id INT NOT NULL REFERENCES students(user_id),
                              course_id INT NOT NULL REFERENCES courses(id),
                              UNIQUE(student_id, course_id),
                              issue_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE quiz_results (
                              id SERIAL PRIMARY KEY,
                              enrollment_id INT NOT NULL REFERENCES enrollments(id),
                              quiz_id INT NOT NULL REFERENCES quizzes(id),
                              score DOUBLE PRECISION NOT NULL,
                              completed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);