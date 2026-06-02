package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    // o intrebare o data creata nu se mai modifica
    private int id;
    private String title;
    private String correctAnswer;
    private List<String> options;
    private int quizId;

    public Question(int id, String title, String correctAnswer, List<String> options, int quizId) {
        this.id = id;
        this.title = title;
        this.correctAnswer = correctAnswer;
        this.options = options;
        this.quizId = quizId;
    }

    public boolean check(String answer) {
        return correctAnswer.equals(answer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public int getQuizId() { return quizId; }
}
