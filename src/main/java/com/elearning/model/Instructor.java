package com.elearning.model;
import com.elearning.enums.Specialty;

public class Instructor extends User {
    private Specialty specialty;

    public Instructor(int id, String name, String email, String password, Specialty specialty) {
        super(id, name, email, password);
        this.specialty = specialty;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    @Override
    public String getRole() {
        return "Instructor";
    }
}
