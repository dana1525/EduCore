package com.elearning.service;
import com.elearning.model.*;
import com.elearning.repository.QuizResultRepository;

import java.sql.SQLException;
import java.util.List;

public class QuizResultService {
    private final QuizResultRepository quizResultRepository = new QuizResultRepository();
    private AuditService audit = AuditService.getInstance();

    public QuizResult completeQuiz(Enrollment enrollment, Quiz quiz, List<String> answers) throws SQLException {
        List<Question> questions = quiz.getQuestions();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = answers.get(i).trim(); // "1", "2" ...
            String correctAnswer = question.getCorrectAnswer().trim();

            try {
                int optionIndex = Integer.parseInt(userAnswer) - 1;
                List<String> options = question.getOptions();

                if (optionIndex >= 0 && optionIndex < options.size()) {
                    String selectedOptionText = options.get(optionIndex).trim();

                    if (selectedOptionText.equalsIgnoreCase(correctAnswer)) {
                        correctCount++;
                    }
                }
            } catch (NumberFormatException e) {}
        }

        double finalScore = ((double) correctCount / questions.size()) * 100;
        QuizResult result = new QuizResult(enrollment, quiz, finalScore);
        audit.log("complete_quiz");
        return quizResultRepository.save(result);
    }

    public double getBestScore(Student student, Quiz quiz) {
        try {
            return quizResultRepository.findBestScoreByStudentAndQuiz(student.getId(), quiz.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
