package com.elearning;

import com.elearning.model.*;
import com.elearning.model.Module;
import com.elearning.service.*;

import java.util.List;

public class Main {
    static void main() {
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();
        QuizService quizService = new QuizService();
        StudentService studentService = new StudentService();
        CertificateService certificateService = new CertificateService();

        // test data
        Instructor instructor = new Instructor(1, "Marian Preda", "marian.preda@gmail.com", "pass", "Java");

        Course course1 = new Course(1, "Java OOP", "Curs Java", instructor);
        Course course2 = new Course(2, "Baze de date", "SQL", instructor);

        Module module1 = new Module(1, "Mostenire", 1);
        course1.addModule(module1);

        try {
            courseService.addCourse(course1);
            courseService.addCourse(course2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        Student student1 = studentService.registerStudent("Andreea", "andreea@gmail.com", "pass1");
        Student student2 = studentService.registerStudent("Mara", "mara@gmail.com", "pass2");
        Student student3 = studentService.registerStudent("Andrei", "andrei@gmail.com", "pass3");

        System.out.println("--- List all students registered ---");
        studentService.listAllStudents();

        enrollmentService.enrollStudent(student1, course1);
        enrollmentService.enrollStudent(student2, course2);
//        enrollmentService.enrollStudent(student2, course2);

        enrollmentService.enrollStudent(student3, course1);

        System.out.println("--- Sort courses ---");
        courseService.listAllCourses();

        System.out.println("--- Enrollments for student " + student1.getName() + " ---");
        enrollmentService.listEnrollmentsByStudent(student1);

        System.out.println("--- " + course1.getTitle() + " students ---");
        enrollmentService.listStudentsByCourse(course1);

        System.out.println("--- Quiz ---");
        Quiz quiz = quizService.createQuiz("Mostenire", module1);
        quizService.addQuestion(quiz, "Ce cuvant cheie folosim pentru mostenire in Java?", "extends",
                List.of("extends", "implements", "inherits", "super"));
        quizService.listQuestions(quiz);

        double score = quizService.completeQuiz(quiz, List.of("super"));
        studentService.updateProgress(student1, score);
        certificateService.issueCertificate(student1, course1);

        score = quizService.completeQuiz(quiz, List.of("extends"));
        studentService.updateProgress(student1, score);
        certificateService.issueCertificate(student1, course1);
        certificateService.issueCertificate(student1, course1);

        System.out.println("--- Certificates issues for " + student1.getName() + " ---");
        certificateService.listCertificatesById(student1);
    }
}
