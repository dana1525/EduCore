package com.elearning.service;

import com.elearning.model.Student;
import com.elearning.repository.StudentRepository;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
//    private List<Student> students = new ArrayList<>();
//    private static int nextIdx;
    private StudentRepository students = new StudentRepository();

    public Student registerStudent(String name, String email, String password) {
        try {
            if (students.findByEmail(email) != null) {
                throw new IllegalArgumentException("Student already exists: " + email);
            }
            Student student = new Student(name, email, password);
            students.save(student);
            return student;
        } catch (SQLException e) {
            throw new RuntimeException("Error registering student: " + e);
        }

    }

    public void updateProgress(int studentId, double progress) {
        try {
            Student student = students.findById(studentId);
            if (student == null) {
                System.out.println("Student not found");
                return;
            }
            student.setProgress(progress);
            students.update(student);
            System.out.println("Progress updated: " + student.getName() + " -> " + student.getProgress() + " %");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update progress.", e);
        }
    }

    public void listAllStudents() {
        try {
            List<Student> allStudents = students.findAll();
            if (allStudents.isEmpty()) {
                System.out.println("No students registered");
                return;
            }
            for (Student s : allStudents) {
                System.out.println(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing students.", e);
        }
    }

    public void deleteStudent(int id) {
        try {
            if (students.findById(id) == null) {
                throw new IllegalArgumentException("Student not found: " + id);
            }
            students.delete(id);
            System.out.println("Student deleted: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
