package com.elearning.model;

import java.time.LocalDate;

public class Enrollment {
    private int id;
    private Course course;
    private Student student;
    private LocalDate enrollmentDate;

    public Enrollment(int id, Course course, Student student) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.enrollmentDate = LocalDate.now();
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

    @Override
    public String toString() {
        return student.getName() + " -> " + course.getTitle() + "; " + enrollmentDate;
    }
}
