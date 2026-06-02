package com.elearning.service;

import com.elearning.model.Question;
import com.elearning.model.Quiz;
import com.elearning.repository.QuestionRepository;
import com.elearning.repository.QuizRepository;

import java.sql.SQLException;
import java.util.List;

public class QuizService {
    private QuizRepository quizRepository = new QuizRepository();
    private QuestionRepository questionRepository = new QuestionRepository();

    public Quiz createQuiz(String title, int moduleId) throws SQLException {
        Quiz quiz = new Quiz(0, title, moduleId);
        return quizRepository.save(quiz);
    }

    public void addQuestion(Quiz quiz, String text, String correctAnswer, List<String> options) throws SQLException {
        // verificare ca raspunsul corect exista in lista de variante
        if (!options.contains(correctAnswer)) {
            throw new IllegalArgumentException("Correct answer must be one of the options.");
        }
        Question question = new Question(0, text, correctAnswer, options, quiz.getId());
        Question savedQuestion = questionRepository.save(question);
        quiz.addQuestion(savedQuestion);
    }

    public List<Quiz> getAllQuizzes() {
        try {
            return quizRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error listing quizzes.", e);
        }
    }

    public Quiz findById(int id) {
        try {
            return quizRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
