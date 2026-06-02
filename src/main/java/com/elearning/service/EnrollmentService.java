package com.elearning.service;

import com.elearning.enums.EnrollmentStatus;
import com.elearning.model.Course;
import com.elearning.model.Enrollment;
import com.elearning.model.Student;
import com.elearning.repository.EnrollmentRepository;

import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

    public void enrollStudent(Student student, Course course) {
        // verificare daca exista deja o inscriere ACTIVE
        try {
            Enrollment activeEnrollment = enrollmentRepository.findActiveEnrollment(student.getId(), course.getId());
            if (activeEnrollment != null) {
                throw new IllegalArgumentException("Student already enrolled in this course");
            }
            Enrollment newEnrollment = new Enrollment(0, course, student);
            enrollmentRepository.save(newEnrollment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listEnrollmentsByStudent(Student student) {
        try {
            List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
            for (Enrollment e : enrollments) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listStudentsByCourse(Course course) {
        try {
            List<Enrollment> enrollments = enrollmentRepository.findByStudentId(course.getId());
            for (Enrollment e : enrollments) {
                System.out.println(e.getStudent());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelEnrollment(Student student, Course course) {
        try {
            Enrollment e = enrollmentRepository.findByStudentAndCourse(student.getId(), course.getId());
            if (e == null) {
                throw new IllegalArgumentException("Enrollment not found for " + student.getName());
            }
            if (e.getStatus() == EnrollmentStatus.COMPLETED) {
                throw new IllegalStateException("Cannot cancel a completed enrollment.");
            }
            e.setStatus(EnrollmentStatus.CANCELLED);
            enrollmentRepository.update(e);
            System.out.println(student.getName() + " cancelled enrollment for " + course.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeEnrollment(Student student, Course course) {
        try {
            Enrollment e = enrollmentRepository.findByStudentAndCourse(student.getId(), course.getId());
            if (e == null) {
                throw new IllegalArgumentException("Enrollment not found for " + student.getName());
            }
            if (e.getStatus() == EnrollmentStatus.COMPLETED) {
                throw new IllegalStateException("Enrollment already completed by student ." + student.getName());
            }
            if (e.getStatus() == EnrollmentStatus.CANCELLED) {
                throw new IllegalStateException("Cannot complete a cancelled enrollment.");
            }
            e.setStatus(EnrollmentStatus.COMPLETED);
            enrollmentRepository.update(e);
            System.out.println(student.getName() + " completed " + course.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Enrollment findByStudentAndCourse(int studentId, int courseId) {
        try {
            return enrollmentRepository.findByStudentAndCourse(studentId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding enrollment.", e);
        }
    }
}
