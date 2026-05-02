package com.elearning.service;

import com.elearning.model.CourseModule;
import com.elearning.model.Question;
import com.elearning.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizService {
    private List<Quiz> quizzes = new ArrayList<>();
    // stocare si questions pentru cautare ulterioara dupa id ??
    private static int nextQuizId;
    private static int nextQuestionId;

    public Quiz createQuiz(String title, CourseModule module) {
        Quiz quiz = new Quiz(++nextQuizId, title);
        module.addQuiz(quiz);
        quizzes.add(quiz);
        System.out.println("Quiz created: " + title + " in module " + module.getTitle());
        return quiz;
    }

    public void addQuestion(Quiz quiz, String text, String correctAnswer, List<String> options) {
        // verificare ca raspunsul corect exista in lista de variante
        if (!options.contains(correctAnswer)) {
            throw new IllegalArgumentException("Correct answer must be one of the options.");
        }
        Question question = new Question(++nextQuestionId, text, correctAnswer, options);
        quiz.addQuestion(question);
        System.out.println("Question added to quiz: " + quiz.getTitle());
    }

    public void listQuestions(Quiz quiz) {
        System.out.println("Quiz: " + quiz.getTitle());
        for (Question q : quiz.getQuestions()) {
            System.out.println(" - " + q.getTitle());
            for (String o : q.getOptions()) {
                System.out.println("    * " + o);
            }
        }
    }
}
