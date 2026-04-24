package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class CourseModule {
    private static int nextId;
    private final int id;
    private String title;
    private int position;
    private List<Quiz> quizzes;

    public CourseModule(String title, int position) {
        this.id = ++nextId;
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
