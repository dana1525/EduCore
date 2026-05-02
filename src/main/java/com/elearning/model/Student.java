package com.elearning.model;

public class Student extends User {
    private double progress;

    public Student(int id, String name, String email, String password) {
        super(id, name, email, password);
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
