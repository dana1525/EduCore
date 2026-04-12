package com.elearning.model;

import java.util.List;

public class Question {
    private int id;
    private String title;
    private String correctAnswer;
    private List<String> options;

    public Question(int id, String title, String correctAnswer, List<String> options) {
        this.id = id;
        this.title = title;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public boolean check(String answer) {
        return correctAnswer.equals(answer);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }
}
