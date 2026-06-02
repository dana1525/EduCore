package com.elearning.service;

import com.elearning.model.Student;
import com.elearning.repository.StudentRepository;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentRepository studentRepository = new StudentRepository();
    private AuditService audit = AuditService.getInstance();

    public Student registerStudent(String name, String email, String password) {
        try {
            if (studentRepository.findByEmail(email) != null) {
                throw new IllegalArgumentException("Student already exists: " + email);
            }
            Student student = new Student(name, email, password);
            studentRepository.save(student);
            audit.log("register_student");
            return student;
        } catch (SQLException e) {
            throw new RuntimeException("Error registering student: " + e);
        }

    }

    public void updateProgress(int studentId, double progress) {
        try {
            Student student = studentRepository.findById(studentId);
            if (student == null) {
                System.out.println("Student not found");
                return;
            }
            student.setProgress(progress);
            studentRepository.update(student);
            audit.log("update_progress");
            System.out.println("Progress updated: " + student.getName() + " -> " + student.getProgress() + " %");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update progress.", e);
        }
    }

    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error listing students.", e);
        }
    }

    public void deleteStudent(int id) {
        try {
            if (studentRepository.findById(id) == null) {
                throw new IllegalArgumentException("Student not found: " + id);
            }
            studentRepository.delete(id);
            System.out.println("Student deleted: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student findById(int id) {
        try {
            return studentRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
