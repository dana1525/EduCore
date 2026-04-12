package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private int id;
    private String title;
    private int position;
    private List<Quiz> quizzes;

    public Module(int id, String title, int position) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.quizzes = new ArrayList<>();
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public List<Quiz> getQuizzes() {
        return new ArrayList<>(quizzes);
    }
}
