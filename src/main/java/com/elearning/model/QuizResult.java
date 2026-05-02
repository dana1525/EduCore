package com.elearning.model;

import java.time.LocalDateTime;

public class QuizResult {
    private final int id;
    private final Student student;
    private final Quiz quiz;
    private final double score;
    private final LocalDateTime completedAt;

    public QuizResult(int id, Student student, Quiz quiz, double score) {
        this.id = id;
        this.student = student;
        this.quiz = quiz;
        this.score = score;
        this.completedAt = LocalDateTime.now();
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public double getScore() {
        return score;
    }
}
