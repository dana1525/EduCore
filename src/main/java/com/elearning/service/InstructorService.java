package com.elearning.service;

import com.elearning.enums.Specialty;
import com.elearning.model.Instructor;

import java.util.ArrayList;
import java.util.List;

public class InstructorService {
    private List<Instructor> instructors = new ArrayList<>();
    private static int nextId;

    public Instructor registerInstructor(String name, String email, String password, Specialty specialty) {
        boolean alreadyExists = instructors.stream()
                .anyMatch(i -> i.getEmail().equals(email));

        if(alreadyExists) {
            throw new IllegalArgumentException("Instructor already exists: " + email);
        }

        Instructor instructor = new Instructor(++nextId, name, email, password, specialty);
        instructors.add(instructor);
        return instructor;
    }

    public void listAllInstructors() {
        instructors.forEach(System.out::println);
    }

    public Instructor findById(int id) {
        return instructors.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Instructor> findBySpecialty(Specialty specialty) {
        return instructors.stream()
                .filter(i -> i.getSpecialty() == specialty)
                .toList();
    }
}
