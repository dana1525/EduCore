package com.elearning.service;

import com.elearning.enums.EnrollmentStatus;
import com.elearning.model.Course;
import com.elearning.model.Enrollment;
import com.elearning.model.Student;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();
    private static int nextId;

    public void enrollStudent(Student student, Course course) {
        // verificare daca exista deja o inscriere ACTIVE
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == student.getId()
                    && e.getCourse().getId() == course.getId()
                    && e.getStatus() == EnrollmentStatus.ACTIVE) {
                throw new IllegalArgumentException("Student already enrolled in this course");
            }
        }
        enrollments.add(new Enrollment(++nextId, course, student));
    }

    public void listEnrollmentsByStudent(Student student) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == student.getId()){
                System.out.println(e);
            }
        }
    }

    public void listStudentsByCourse(Course course) {
        for (Enrollment e : enrollments) {
            if (e.getCourse().getId() == course.getId()) {
                System.out.println(e.getStudent());
            }
        }
    }

    public void cancelEnrollment(Student student, Course course) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == student.getId()
                    && e.getCourse().getId() == course.getId()) {
                if (e.getStatus() == EnrollmentStatus.COMPLETED) {
                    throw new IllegalStateException("Cannot cancel a completed enrollment.");
                }
                e.setStatus(EnrollmentStatus.CANCELLED);
                System.out.println(student.getName() + " cancelled enrollment for " + course.getTitle());
                return;
            }
        }
    }

    public void completeEnrollment(Student student, Course course) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == student.getId() && e.getCourse().getId() == course.getId()) {
                if (e.getStatus() == EnrollmentStatus.COMPLETED) {
                    throw new IllegalStateException("Enrollment already completed by student ." + student.getName());
                }
                if (e.getStatus() == EnrollmentStatus.CANCELLED) {
                    throw new IllegalStateException("Cannot complete a cancelled enrollment.");
                }
                e.setStatus(EnrollmentStatus.COMPLETED);
                System.out.println(student.getName() + " completed " + course.getTitle());
                return;
            }
        }
        throw new IllegalArgumentException("Enrollment not found for " + student.getName());
    }
}
