package com.elearning.model;

import java.util.ArrayList;
import java.util.List;

public class CourseModule {
//    private static int nextId;
    private int id;
    private String title;
    private int position;
    private List<Quiz> quizzes;
    private int course_id;

    public CourseModule(int id, String title, int position, int course_id) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.quizzes = new ArrayList<>();
        this.course_id = course_id;
    }

    public CourseModule(String title, int position, int course_id) {
        this.id = 0;
        this.title = title;
        this.position = position;
        this.quizzes = new ArrayList<>();
        this.course_id = course_id;
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

    public int getCourseId() {
        return course_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Module: " + title + " | Position: " + position;
    }
}
