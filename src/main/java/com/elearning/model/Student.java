package com.elearning.model;

public class Student extends User {
    private double progress;

    // onstructor pentru repo (cand citim din DB)
    public Student(int id, String name, String email, String password, double progress) {
        super(id, name, email, password);
        this.progress = progress;
    }

    // constructor pentru creare
    public Student(String name, String email, String password) {
        super(0, name, email, password); // id = 0 temporar
        this.progress = 0.0;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }
}
