package com.elearning.model;

public class Instructor extends User {
    private String specialty;

    public Instructor(int id, String name, String email, String password, String specialty) {
        super(id, name, email, password);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String getRole() {
        return "Instructor";
    }
}
