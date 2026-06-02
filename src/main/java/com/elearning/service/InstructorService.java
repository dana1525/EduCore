package com.elearning.service;

import com.elearning.enums.Specialty;
import com.elearning.model.Instructor;
import com.elearning.repository.InstructorRepository;

import java.sql.SQLException;
import java.util.List;

public class InstructorService {
    private InstructorRepository instructorRepository = new InstructorRepository();

    public Instructor registerInstructor(String name, String email, String password, Specialty specialty) {
        try {
            if(instructorRepository.findByEmail(email) != null) {
                throw new IllegalArgumentException("Instructor already exists: " + email);
            }
            Instructor instructor = new Instructor(name, email, password, specialty);
            return instructorRepository.save(instructor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Instructor> getAllInstructors() {
        try {
            return instructorRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Instructor findById(int id) {
        try {
            return instructorRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
