package com.elearning.service;

import com.elearning.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students = new ArrayList<>();
    private static int nextIdx;

    public Student registerStudent(String name, String email, String password) {
        for (Student s : students) {
            if (s.getEmail().equals(email)) {
                throw new IllegalArgumentException("Student already exists: " + email);
            }
        }
        Student student = new Student(++nextIdx, name, email, password);
        students.add(student);
        return student;
    }

    public void updateProgress(Student student, double progress) {
        student.setProgress(progress);
        System.out.println("Progress updated: " + student.getName() + " -> " + student.getProgress() + " %");
    }

    public void listAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
