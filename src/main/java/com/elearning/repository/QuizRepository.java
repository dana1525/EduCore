package com.elearning.repository;

import com.elearning.model.Question;
import com.elearning.model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizRepository extends GenericRepository<Quiz> {
    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM quizzes WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM quizzes";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM quizzes WHERE id = ?";
    }

    @Override
    protected Quiz mapRow(ResultSet rs) throws SQLException {
        return new Quiz(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("module_id")
        );
    }

    @Override
    public Quiz save(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO quizzes (title, module_id) VALUES (?, ?)";
        int id = executeInsert(sql, quiz.getTitle(), quiz.getModuleId());
        quiz.setId(id);
        return quiz;
    }

    @Override
    public void update(Quiz quiz) throws SQLException {
        String sql = "UPDATE quizzes SET title = ?, module_id = ? WHERE id = ?";
        executeUpdate(sql, quiz.getTitle(), quiz.getModuleId(), quiz.getId());
    }

    public Quiz findById(int id) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Quiz quiz = new Quiz(rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("module_id"));
                    List<Question> questions = findQuestionsForQuiz(id);
                    quiz.setQuestions(questions);
                    return quiz;
                }
            }
        }
        return null;
    }

    private List<Question> findQuestionsForQuiz(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("text");
                    String correctAnswer = rs.getString("correct_answer");

                    List<String> options = findOptionsForQuestion(id);

                    Question q = new Question(id, title, correctAnswer, options, quizId);
                    questions.add(q);
                }
            }
        }
        return questions;
    }

    private List<String> findOptionsForQuestion(int questionId) throws SQLException {
        List<String> options = new ArrayList<>();
        String sql = "SELECT text FROM options WHERE question_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    options.add(rs.getString("text"));
                }
            }
        }
        return options;
    }
}
