package com.elearning.repository;

import com.elearning.model.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository extends GenericRepository<Question> {
    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM questions WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM questions";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM questions WHERE id = ?";
    }

    @Override
    protected Question mapRow(ResultSet rs) throws SQLException {
        int questionId = rs.getInt("id");
        List<String> options = findOptionByQuestionId(questionId);
        return new Question(
                questionId,
                rs.getString("text"),
                rs.getString("correct_answer"),
                options,
                rs.getInt("quiz_id")
        );
    }

    @Override
    public Question save(Question question) throws SQLException {
        String sql = "INSERT INTO questions (text, correct_answer, quiz_id) VALUES (?, ?, ?)";
        int id = executeInsert(sql, question.getTitle(), question.getCorrectAnswer(), question.getQuizId());
        question.setId(id);

        // save options after getting question id
        String sqlOptions = "INSERT INTO options (text, question_id) VALUES (?, ?)";
        for (String option : question.getOptions()) {
            executeInsert(sqlOptions, option, id);
        }
        return question;
    }

    @Override
    public void update(Question question) throws SQLException {
        String sql = "UPDATE questions SET text = ?, correct_answer = ?, quiz_id = ? WHERE id = ?";
        executeUpdate(sql, question.getTitle(), question.getCorrectAnswer(), question.getQuizId(), question.getId());

        // delete old options and save the new ones
        executeUpdate("DELETE FROM options WHERE question_id = ?", question.getId());
        String sqlOptions = "INSERT INTO options (text, question_id) VALUES (?, ?)";
        for (String option : question.getOptions()) {
            executeInsert(sqlOptions, option, question.getId());
        }
    }

    private List<String> findOptionByQuestionId(int questionId) throws SQLException {
        List<String> options = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT text FROM options WHERE question_id = ?")) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("text"));
            }
        }
        return options;
    }
}
