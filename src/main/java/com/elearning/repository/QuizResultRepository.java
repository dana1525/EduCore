package com.elearning.repository;

import com.elearning.model.Enrollment;
import com.elearning.model.Quiz;
import com.elearning.model.QuizResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizResultRepository extends GenericRepository<QuizResult> {
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private QuizRepository quizRepository = new QuizRepository();

    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM quiz_results WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM quiz_results";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM quiz_results WHERE id = ?";
    }

    @Override
    protected QuizResult mapRow(ResultSet rs) throws SQLException {
        Enrollment enrollment = enrollmentRepository.findById(rs.getInt("enrollment_id"));
        Quiz quiz = quizRepository.findById(rs.getInt("quiz_id"));
        return new QuizResult(
                rs.getInt("id"),
                enrollment,
                quiz,
                rs.getDouble("score"),
                rs.getTimestamp("completed_at").toLocalDateTime()
        );
    }

    @Override
    public QuizResult save(QuizResult quizResult) throws SQLException {
        String sql = "INSERT INTO quiz_results (enrollment_id, quiz_id, score) VALUES (?, ?, ?)";
        int id = executeInsert(sql, quizResult.getEnrollment().getId(),
                quizResult.getQuiz().getId(), quizResult.getScore());
        quizResult.setId(id);
        return quizResult;
    }

    @Override
    public void update(QuizResult quizResult) throws SQLException {
        // Quiz results cannot be modified
        throw new UnsupportedOperationException("Quiz results are definitive and cannot be updated.");
    }

    public double findBestScoreByStudentAndQuiz(int studentId, int quizId) throws SQLException {
        String sql = "SELECT MAX(qr.score) FROM quiz_results qr " +
                "JOIN enrollments e ON qr.enrollment_id = e.id " +
                "WHERE e.student_id = ? AND qr.quiz_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, quizId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }
}
