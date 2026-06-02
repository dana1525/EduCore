package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private int moduleId;
    private List<Question> questions;

    public Quiz(int id, String title, int moduleId) {
        this.id = id;
        this.title = title;
        this.moduleId = moduleId;
        this.questions = new ArrayList<>();
    }

    public Quiz(String title, int moduleId) {
        this.title = title;
        this.moduleId = moduleId;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        this.questions.add(question);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public int getModuleId() { return moduleId; }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
