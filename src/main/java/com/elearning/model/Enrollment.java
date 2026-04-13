package com.elearning.model;

import java.time.LocalDate;

public class Enrollment {
    public enum Status { ACTIVE, COMPLETED, CANCELLED };

    private int id;
    private Course course;
    private Student student;
    private LocalDate enrollmentDate;
    private Status status;

    public Enrollment(int id, Course course, Student student) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.enrollmentDate = LocalDate.now();
        this.status = Status.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return student.getName() + " -> " + course.getTitle() + "; " + enrollmentDate;
    }
}
