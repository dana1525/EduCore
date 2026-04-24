package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    // o intrebare o data create nu se mai modifica
    private final int id;
    private final String title;
    private final String correctAnswer;
    private final List<String> options;

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
        return new ArrayList<>(options);
    }
}
