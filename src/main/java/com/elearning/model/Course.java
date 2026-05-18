package com.elearning.model;
import com.elearning.enums.Difficulty;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String title;
    private String description;
    private Instructor instructor;
    private List<CourseModule> modules;
    private Difficulty difficulty;

    public Course(int id, String title, String description, Instructor instructor, Difficulty difficulty) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.difficulty = difficulty;
        this.modules = new ArrayList<>();
    }

    public void addModule(CourseModule module) {
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

    public Difficulty getDifficulty() { return difficulty; }

    public List<CourseModule> getModule() {
        return new ArrayList<>(modules);
    }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "Course: " + title + " | Instuctor: " + instructor.getName();
    }
}
