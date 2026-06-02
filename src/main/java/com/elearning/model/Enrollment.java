package com.elearning.model;
import com.elearning.enums.EnrollmentStatus;
import java.time.LocalDate;

public class Enrollment {
    private int id;
    private final Course course;
    private final Student student;
    private final LocalDate enrollmentDate;
    private EnrollmentStatus status;

    public Enrollment(int id, Course course, Student student) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    public Enrollment(int id, Course course, Student student, LocalDate enrollmentDate, EnrollmentStatus status) {
        this.id = id;
        this.course = course;
        this.student = student;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
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

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return student.getName() + " -> " + course.getTitle() + "; " + enrollmentDate;
    }
}
