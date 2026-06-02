package com.elearning;

import com.elearning.enums.Difficulty;
import com.elearning.enums.EnrollmentStatus;
import com.elearning.enums.Specialty;
import com.elearning.model.*;
import com.elearning.repository.*;
import com.elearning.service.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // INITIALIZE SERVICES
    private static final InstructorService instructorService = new InstructorService();
    private static final CourseService courseService = new CourseService();
    private static final StudentService studentService = new StudentService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final QuizService quizService = new QuizService();
    private static final QuizResultService quizResultService = new QuizResultService();
    private static final CertificateService certificateService = new CertificateService();

    private static final Scanner scanner = new Scanner(System.in);

    static void main() throws SQLException {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readIntegerInput();
            try {
                switch (choice) {
                    // --- ADMIN /INSTRUCTOR ---
                    case 1 -> registerNewInstructor();
                    case 2 -> createNewCourse();
                    case 3 -> createNewModule();
                    case 4 -> createNewQuiz();
                    case 5 -> addQuestionToQuiz();

                    // --- STUDENT ---
                    case 6 -> registerNewStudent();
                    case 7 -> enrollStudentToCourse();
                    case 8 -> takeQuizFlow();
                    case 9 -> viewStudentCertificates();

                    // --- VISUALIZE DATA ---
                    case 10 -> {
                        System.out.println("\n--- All Courses ---");
                        List<Course> courses = courseService.getAllCourses();
                        if (courses.isEmpty()) {
                            System.out.println("No courses registered yet.");
                        }
                        for (Course course : courses) {
                            System.out.println("COURSE: " + course.getTitle() + " [" + course.getDifficulty() + "]");
                            System.out.println("Instructor: " + course.getInstructor().getName());
                            if (course.getModules().isEmpty()) {
                                System.out.println("  (No modules yet)");
                            } else {
                                for (CourseModule m : course.getModules()) {
                                    System.out.println("  -> [" + m.getId() + "] " + m.getTitle());
                                }
                            }
                            System.out.println("--------------------------------------------------");
                        }
                    }
                    case 11 -> {
                        System.out.println("All Students");
                        showAvailableStudents();
                    }
                    case 12 -> {
                        System.out.println("All Instructors");
                        showAvailableInstructors();
                    }
                    case 0 -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Try again!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("================ E-LEARNING SYSTEM ================");
        System.out.println("--- Course Management (Instructors /Admins) ---");
        System.out.println("1. Register Instructor");
        System.out.println("2. Add New Course");
        System.out.println("3. Add Module to Course");
        System.out.println("4. Create Quiz for Module");
        System.out.println("5. Add Question to Quiz");
        System.out.println("--- Student Management ---");
        System.out.println("6. Register Student");
        System.out.println("7. Enroll Student in Course");
        System.out.println("8. Take a Quiz (Test)");
        System.out.println("9. View Student Certificates");
        System.out.println("--- REPORTS ---");
        System.out.println("10. List All Courses");
        System.out.println("11. List All Students");
        System.out.println("12. List All Instructors");
        System.out.println("0. Exit");
        System.out.println("Choose an option: ");
    }

    // -----
    private static void registerNewInstructor() {
        System.out.print("Instructor Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        Specialty specialty = readSpecialty();

        Instructor i = instructorService.registerInstructor(name, email, pass, specialty);
        System.out.println("Instructor saved with ID: " + i.getId());
    }

    private static void registerNewStudent() {
        System.out.print("Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Student s = studentService.registerStudent(name, email, pass);
        System.out.println("Student saved with ID: " + s.getId());
    }

    private static void createNewCourse() throws SQLException {
        if (!showAvailableInstructors()) return;

        System.out.print("\nEnter Instructor ID from the list above: ");
        int instructorId = readIntegerInput();

        Instructor instructor = instructorService.findById(instructorId);
        if (instructor == null) {
            System.out.println("Error: Instructor with this ID does not exist!");
            return;
        }

        System.out.print("Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        Difficulty difficulty = readDifficulty();

        Course c = courseService.addCourse(title, description, instructor, difficulty);
        System.out.println("Course added with ID: " + c.getId());
    }

    private static void createNewModule() throws SQLException {
        if (!showAvailableCourses()) return;

        // Prompt for the ID based on the list above
        System.out.print("\nEnter Course ID to which this module belongs: ");
        int courseId = readIntegerInput();

        Course course = courseService.findById(courseId);
        if (course == null) {
            System.out.println("Error: Course with ID " + courseId + " does not exist!");
            return;
        }

        System.out.print("Module Title (e.g., Inheritance and Polymorphism): ");
        String title = scanner.nextLine();
        System.out.print("Module Order Number: ");
        int order = readIntegerInput();

        CourseModule module = courseService.addModuleToCourse(courseId, title, order);
        System.out.println("Module '" + module.getTitle() + "' was added with ID: " + module.getId());
    }

    private static void enrollStudentToCourse() throws SQLException {
        if (!showAvailableStudents()) return;
        System.out.print("\nEnter Student ID: ");
        int studentId = readIntegerInput();

        if (!showAvailableCourses()) return;
        System.out.print("\nEnter Course ID: ");
        int courseId = readIntegerInput();

        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);

        if (student == null || course == null) {
            System.out.println("Error: Student or Course does not exist!");
            return;
        }

        enrollmentService.enrollStudent(student, course);
        System.out.println("Enrollment completed successfully!");
    }

    private static void createNewQuiz() throws SQLException {
        if (!showAvailableCourses()) return;

        System.out.print("\nSelect Course ID for which you want to create a quiz: ");
        int courseId = readIntegerInput();

        Course course = courseService.findById(courseId);
        if (course == null) {
            System.out.println("Error: Course with this ID does not exist!");
            return;
        }

        if (!showAvailableModules(courseId, course.getTitle())) return;

        System.out.print("\nEnter Module ID from the list above: ");
        int moduleId = readIntegerInput();

        CourseModule module = courseService.getModuleById(moduleId);
        if (module == null || module.getCourseId() != courseId) { // extra-validare de siguranță
            System.out.println("Error: Invalid Module ID for this course!");
            return;
        }

        System.out.print("Quiz Title: ");
        String title = scanner.nextLine();

        Quiz quiz = quizService.createQuiz(title, module.getId());
        System.out.println("Quiz created with ID: " + quiz.getId());
    }

    private static void addQuestionToQuiz() throws SQLException {
        if (!showAvailableQuizzes()) return;

        System.out.print("\nEnter Quiz ID to add the question to: ");
        int quizId = readIntegerInput();

        Quiz quiz = quizService.findById(quizId);
        if (quiz == null) {
            System.out.println("Error: Quiz not found!");
            return;
        }

        System.out.print("Question Text: ");
        String title = scanner.nextLine();
        System.out.print("Correct Answer (must match one option exactly): ");
        String correctAnswer = scanner.nextLine();

        List<String> options = new ArrayList<>();
        System.out.println("Enter 4 answer options:");
        for (int i = 1; i <= 4; i++) {
            System.out.print("Option " + i + ": ");
            options.add(scanner.nextLine());
        }

        quizService.addQuestion(quiz, title, correctAnswer, options);
        System.out.println("Question added to the quiz.");
    }

    private static void takeQuizFlow() throws SQLException {
        if (!showAvailableStudents()) return;
        System.out.print("\nEnter Student ID: ");
        int studentId = readIntegerInput();

        if (!showAvailableCourses()) return;
        System.out.print("Enter Course ID: ");
        int courseId = readIntegerInput();

        Enrollment enrollment = enrollmentService.findByStudentAndCourse(studentId, courseId);
        if (enrollment == null) {
            System.out.println("Error: This student is not enrolled in the selected course!");
            return;
        }

        if (!showAvailableQuizzes()) return;
        System.out.print("\nEnter Quiz ID from the list above: ");
        int quizId = readIntegerInput();
        Quiz quiz = quizService.findById(quizId);

        if (quiz == null) {
            System.out.println("Error: Quiz not found!");
            return;
        }

        List<Question> questions = quiz.getQuestions();
        if (questions.isEmpty()) {
            System.out.println("This quiz does not have any questions yet.");
            return;
        }

        List<String> userAnswers = new ArrayList<>();
        System.out.println("\n--- Quiz Started: " + quiz.getTitle() + " ---");
        for (Question q : questions) {
            System.out.println("\nQuestion: " + q.getTitle());
            for (int i = 0; i < q.getOptions().size(); i++) {
                System.out.println((i + 1) + ") " + q.getOptions().get(i));
            }
            System.out.print("Your answer: ");
            userAnswers.add(scanner.nextLine());
        }

        QuizResult result = quizResultService.completeQuiz(enrollment, quiz, userAnswers);
        System.out.println("Your score for this attempt: " + result.getScore() + "/100");

        double bestScore = quizResultService.getBestScore(enrollment.getStudent(), quiz);
        studentService.updateProgress(studentId, bestScore);
        System.out.println("Your all-time best score: " + bestScore + "/100");

        // reload student from DB with updated progress
        Student updatedStudent = studentService.findById(studentId);

        if (bestScore >= 70.0) {
            if (enrollment.getStatus() != EnrollmentStatus.COMPLETED) {
                enrollmentService.completeEnrollment(updatedStudent, enrollment.getCourse());
                System.out.println("Congratulations! You have completed the course.");
            }
            certificateService.issueCertificate(updatedStudent, enrollment.getCourse());
        }
    }

    private static void viewStudentCertificates() {
        if (!showAvailableStudents()) return;

        System.out.print("\nEnter Student ID to view certificates: ");
        int studentId = readIntegerInput();
        Student student = studentService.findById(studentId);

        if (student == null) {
            System.out.println("Error: Student does not exist!");
            return;
        }

        System.out.println("\n--- Certificates for " + student.getName() + " ---");
        certificateService.listCertificatesById(student);
    }


    private static int readIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // -- Helper Methods
    private static boolean showAvailableInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        if (instructors.isEmpty()) {
            System.out.println("No instructors found. Please register an instructor first.");
            return false;
        }
        System.out.println("\n--- Available Instructors ---");
        for (Instructor inst : instructors) {
            System.out.println("[" + inst.getId() + "] " + inst.getName() + " (" + inst.getSpecialty() + ")");
        }
        return true;
    }

    private static boolean showAvailableStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found. Register a student first.");
            return false;
        }
        System.out.println("\n--- Available Students ---");
        for (Student s : students) System.out.println("[" + s.getId() + "] " + s.getName());
        return true;
    }

    private static boolean showAvailableCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found. Register a course first");
            return false;
        }
        System.out.println("\n--- Available Courses ---");
        for (Course c: courses) System.out.println("[" + c.getId() + "]" + c.getTitle());
        return true;
    }

    private static boolean showAvailableQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes found. Register a quiz first");
            return false;
        }
        System.out.println("\n--- Available Quizzes ---");
        for (Quiz q : quizzes) System.out.println("[" + q.getId() + "]" + q.getTitle());
        return true;
    }

    private static boolean showAvailableModules(int courseId, String courseTitle) {
        List<CourseModule> modules = courseService.getModulesByCourse(courseId);

        if (modules.isEmpty()) {
            System.out.println("This course ('" + courseTitle + "') doesn't have any modules yet. Create a module first!");
            return false;
        }

        System.out.println("\n--- Available Modules for Course: " + courseTitle + " ---");
        for (CourseModule mod : modules) {
            System.out.println("[" + mod.getId() + "] Order " + mod.getPosition() + ": " + mod.getTitle());
        }
        return true;
    }


    private static Specialty readSpecialty() {
        while (true) {
            try {
                System.out.print("Specialty (JAVA, PYTHON, DATABASE, WEB_DEVELOPMENT, DATA_SCIENCE, UI_UX): ");
                return Specialty.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid specialty. Try again.");
            }
        }
    }

    private static Difficulty readDifficulty() {
        while (true) {
            try {
                System.out.print("Difficulty (BEGINNER, INTERMEDIATE, ADVANCED): ");
                return Difficulty.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid difficulty. Try again.");
            }
        }
    }
}
