package com.elearning.repository;

import com.elearning.model.Certificate;
import com.elearning.model.Course;
import com.elearning.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CertificateRepository extends GenericRepository<Certificate> {
    CourseRepository courseRepository = new CourseRepository();
    StudentRepository studentRepository = new StudentRepository();

    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM certificates WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM certificates";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM certificates WHERE id = ?";
    }

    @Override
    protected Certificate mapRow(ResultSet rs) throws SQLException {
        Course course = courseRepository.findById(rs.getInt("course_id"));
        Student student = studentRepository.findById(rs.getInt("student_id"));
        return new Certificate(
                rs.getInt("id"),
                course,
                student,
                rs.getDate("issue_date").toLocalDate()
        );
    }

    @Override
    public Certificate save(Certificate certificate) throws SQLException {
        String sql = "INSERT INTO certificates (student_id, course_id, issue_date) VALUES (?, ?, ?)";
        int id = executeInsert(sql, certificate.getStudent().getId(),
                certificate.getCourse().getId(), certificate.getIssueDate());
        certificate.setId(id);
        return certificate;
    }

    @Override
    public void update(Certificate certificate) throws SQLException {
        // An issued certificate cannot be modified
        throw new UnsupportedOperationException("Certificates are immutable and cannot be updated.");
    }

    public Certificate findByStudentAndCourse(int studentId, int courseId) throws SQLException {
        String sql = "SELECT * FROM certificates WHERE student_id = ? AND course_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
             stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    public List<Certificate> findByStudentId(int studentId) throws SQLException {
        String sql = "SELECT * FROM certificates WHERE student_id = ?";
        List<Certificate> certificates = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                certificates.add(mapRow(rs));
            }
        }
        return certificates;
    }
}
