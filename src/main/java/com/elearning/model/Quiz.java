package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final int id;
    private String title;
    private List<Question> questions;

    public Quiz(int id, String title) {
        this.id = id;
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }
}
