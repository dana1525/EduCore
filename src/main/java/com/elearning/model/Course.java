package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String title;
    private String description; // ??
    private Instructor instructor;
    private List<Module> modules;

    public Course(int id, String title, String description, Instructor instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.modules = new ArrayList<>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public List<Module> getModule() {
        return modules;
    }

    @Override
    public String toString() {
        return "Course: " + title + " | Instuctor: " + instructor.getName();
    }
}
