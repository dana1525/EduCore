package com.elearning;

import com.elearning.model.Course;
import com.elearning.model.Instructor;
import com.elearning.model.Module;
import com.elearning.model.Student;
import com.elearning.service.CourseService;
import com.elearning.service.EnrollmentService;

public class Main {
    static void main() {
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();

        // test data
        Instructor instructor = new Instructor(1, "Marian Preda", "marian.preda@gmail.com", "pass", "Java");
        Student student1 = new Student(1, "Andreea", "andreea@gmail.com", "pass");
        Student student2 = new Student(2, "Mara", "mara@gmail.com", "pass");

        Course course1 = new Course(1, "Java OOP", "Curs Java", instructor);
        Course course2 = new Course(2, "Baze de date", "SQL", instructor);

        Module module1 = new Module(1, "Tipuri de date", 1);
        course1.addModule(module1);

        try {
            courseService.addCourse(course1);
            courseService.addCourse(course2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        enrollmentService.enrollStudent(student1, course1);
        enrollmentService.enrollStudent(student2, course2);

        System.out.println("--- Sort courses ---");
        courseService.listAllCourses();

        System.out.println("--- Enrollments for student " + student1.getName());
        enrollmentService.listEnrollmentsByStudent(student1);

        System.out.println("--- " + course1.getTitle() + " students ---");
        enrollmentService.listStudentsByCourse(course1);
    }
}
