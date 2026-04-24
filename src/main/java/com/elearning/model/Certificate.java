package com.elearning.model;

import java.time.LocalDate;

public class Certificate {
    // un certificat odata emis nu se modifica niciodata
    private final int id;
    private final Course course;
    private final Student student;
    private final LocalDate issueDate;

    public Certificate(int id, Course course, Student student) {
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
