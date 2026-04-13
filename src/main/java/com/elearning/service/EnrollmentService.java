package com.elearning.service;

import com.elearning.model.Course;
import com.elearning.model.Enrollment;
import com.elearning.model.Student;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();
    private int nextId;

    public void enrollStudent(Student student, Course course) {
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
                if (e.getStatus() == Enrollment.Status.COMPLETED) {
                    throw new IllegalStateException("Cannot cancel a completed enrollment.");
                }
                e.setStatus(Enrollment.Status.CANCELLED);
                System.out.println(student.getName() + " cancelled enrollment for " + course.getTitle());
                return;
            }
        }
    }

    public void completeEnrollment(Student student, Course course) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId() == student.getId() && e.getCourse().getId() == course.getId()) {
                if (e.getStatus() == Enrollment.Status.COMPLETED) {
                    throw new IllegalStateException("Enrollment already completed.");
                }
                if (e.getStatus() == Enrollment.Status.CANCELLED) {
                    throw new IllegalStateException("Cannot complete a cancelled enrollment.");
                }
                e.setStatus(Enrollment.Status.COMPLETED);
                System.out.println(student.getName() + " completed " + course.getTitle());
                return;
            }
        }
        throw new IllegalArgumentException("Enrollment not found for " + student.getName());
    }
}
