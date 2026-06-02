package com.elearning.model;

import java.time.LocalDateTime;

public class QuizResult {
    private int id;
    private final Enrollment enrollment;
    private final Quiz quiz;
    private final double score;
    private final LocalDateTime completedAt;

    public QuizResult(Enrollment enrollment, Quiz quiz, double score) {
        this.enrollment = enrollment;
        this.quiz = quiz;
        this.score = score;
        this.completedAt = LocalDateTime.now();
    }

    public QuizResult(int id, Enrollment enrollment, Quiz quiz, double score, LocalDateTime completedAt) {
        this.id = id;
        this.enrollment = enrollment;
        this.quiz = quiz;
        this.score = score;
        this.completedAt = completedAt;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Enrollment getEnrollment() { return enrollment; }

    public Quiz getQuiz() {
        return quiz;
    }

    public double getScore() { return score; }

    public LocalDateTime getCompletedAt() { return completedAt; }
}
