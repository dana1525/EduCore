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
}
