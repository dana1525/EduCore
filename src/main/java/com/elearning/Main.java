package com.elearning;

import com.elearning.enums.Difficulty;
import com.elearning.enums.Specialty;
import com.elearning.model.*;
import com.elearning.service.*;

import java.util.List;

public class Main {
    static void main() {
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();
        QuizService quizService = new QuizService();
        QuizResultService quizResultService = new QuizResultService();
        StudentService studentService = new StudentService();
        CertificateService certificateService = new CertificateService();
        InstructorService instructorService = new InstructorService();

        // test data
        try {
            Instructor instructor1 = instructorService.registerInstructor("Marian Preda", "marian.preda@gmail.com", "pass", Specialty.JAVA);
            Instructor instructor2 = instructorService.registerInstructor( "Ana Andrei", "ana123@gmail.com", "pass", Specialty.DATABASE);

            System.out.println("--- List all instructors registered ---");
            instructorService.listAllInstructors();

            System.out.println("--- List all JAVA instructors ---");
            System.out.println(instructorService.findBySpecialty(Specialty.JAVA));

            Course course1 = courseService.addCourse("Java OOP", "Curs Java", instructor1, Difficulty.ADVANCED);
            Course course2 = courseService.addCourse("Baze de date", "SQL", instructor2, Difficulty.INTERMEDIATE);

            CourseModule module1 = courseService.addModuleToCourse(course1.getId(), "Mostenire", 1);

            System.out.println("--- Sort courses ---");
            courseService.listAllCourses();

            Student student1 = studentService.registerStudent("Andreea", "andreea@gmail.com", "pass1");
            Student student2 = studentService.registerStudent("Mara", "mara@gmail.com", "pass2");
            Student student3 = studentService.registerStudent("Andrei", "andrei@gmail.com", "pass3");
//            Student student4 = studentService.registerStudent("Ana", "andrei@gmail.com", "pass4");

            System.out.println("--- List all students registered ---");
            studentService.listAllStudents();

            enrollmentService.enrollStudent(student1, course1);
            enrollmentService.enrollStudent(student2, course2);
            enrollmentService.enrollStudent(student3, course1);

            System.out.println("--- Enrollments for student " + student1.getName() + " ---");
            enrollmentService.listEnrollmentsByStudent(student1);

            System.out.println("--- " + course1.getTitle() + " students ---");
            enrollmentService.listStudentsByCourse(course1);

            System.out.println("--- Quiz ---");
            Quiz quiz = quizService.createQuiz("Mostenire", module1);
            quizService.addQuestion(quiz, "Ce cuvant cheie folosim pentru mostenire in Java?", "extends",
                    List.of("extends", "implements", "inherits", "super"));
            quizService.addQuestion(quiz, "Ce cuvant cheie este folosit in interiorul unui constructor din subclasa pentru a apela constructorul superclasei?", "super()",
                    List.of("parent()", "this()", "super()", "extends()"));
            quizService.listQuestions(quiz);

            QuizResult result1 = quizResultService.completeQuiz(quiz, student1, List.of("super", "super()")); // raspunsuri gresite
            studentService.updateProgress(student1.getId(), quizResultService.getBestScore(student1, quiz));
            certificateService.issueCertificate(student1, course1);

            QuizResult result2 = quizResultService.completeQuiz(quiz, student1, List.of("extends", "super()"));
            studentService.updateProgress(student1.getId(), quizResultService.getBestScore(student1, quiz));

            try {
                enrollmentService.completeEnrollment(student1, course1); // completare inscriere
                certificateService.issueCertificate(student1, course1); // emitere certificat
//                certificateService.issueCertificate(student1, course1); // esueaza, certificat deja emis
//                enrollmentService.cancelEnrollment(student1, course1); // esueaza, inscrierea e deja completed
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("--- Certificates issues for " + student1.getName() + " ---");
            certificateService.listCertificatesById(student1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

//        Connection conn = DatabaseConnection.getInstance().getConnection();
    }
}
