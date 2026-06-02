package com.elearning.model;

import java.time.LocalDate;

public class Certificate {
    // un certificat odata emis nu se modifica niciodata
    private int id;
    private final Course course;
    private final Student student;
    private final LocalDate issueDate;

    public Certificate(int id, Course course, Student student) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.issueDate = LocalDate.now();
    }

    public Certificate(int id, Course course, Student student, LocalDate issueDate) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.issueDate = issueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

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
