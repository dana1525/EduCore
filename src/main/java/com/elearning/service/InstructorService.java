package com.elearning.service;

import com.elearning.enums.Specialty;
import com.elearning.model.Instructor;
import com.elearning.repository.InstructorRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorService {
//    private List<Instructor> instructors = new ArrayList<>();
//    private static int nextId;
    private InstructorRepository repository = new InstructorRepository();

    public Instructor registerInstructor(String name, String email, String password, Specialty specialty) {
        try {
            if(repository.findByEmail(email) != null) {
                throw new IllegalArgumentException("Instructor already exists: " + email);
            }
            Instructor instructor = new Instructor(name, email, password, specialty);
            return repository.save(instructor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void listAllInstructors() {
        try {
            repository.findAll().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Instructor findById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Instructor> findBySpecialty(Specialty specialty) {
//        return instructors.stream()
//                .filter(i -> i.getSpecialty() == specialty)
//                .toList();
//    }
}
