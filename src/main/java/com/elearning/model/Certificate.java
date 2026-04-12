package com.elearning.model;

import java.time.LocalDate;

public class Certificate {
    private int id;
    private Course course;
    private Student student;
    private LocalDate issueDate;

    public Certificate(int id, Course course, Student student, LocalDate issueDate) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.issueDate = LocalDate.now();
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    @Override
    public String toString() {
        return "Certificate: " + student.getName() + " completed " + course.getTitle();
    }
}
