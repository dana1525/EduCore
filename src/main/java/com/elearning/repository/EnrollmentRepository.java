package com.elearning.repository;

import com.elearning.enums.EnrollmentStatus;
import com.elearning.model.Course;
import com.elearning.model.Enrollment;
import com.elearning.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository extends GenericRepository<Enrollment> {
    private CourseRepository courseRepository = new CourseRepository();
    private StudentRepository studentRepository = new StudentRepository();

    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM enrollments WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM enrollments";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM enrollments WHERE id = ?";
    }

    @Override
    protected Enrollment mapRow(ResultSet rs) throws SQLException {
        Course course = courseRepository.findById(rs.getInt("course_id"));
        Student student = studentRepository.findById(rs.getInt("student_id"));
        return new Enrollment(
                rs.getInt("id"),
                course,
                student,
                rs.getDate("enrollment_date").toLocalDate(),
                EnrollmentStatus.valueOf(rs.getString("status"))
        );
    }

    @Override
    public Enrollment save(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date, status) VALUES (?, ?, ?, ?)";
        int id = executeInsert(sql, enrollment.getStudent().getId(),
                enrollment.getCourse().getId(), enrollment.getEnrollmentDate(), enrollment.getStatus().name());
        enrollment.setId(id);
        return enrollment;
    }

    @Override
    public void update(Enrollment enrollment) throws SQLException {
        String sql = "UPDATE enrollments SET status = ? WHERE id = ?";
        executeUpdate(sql, enrollment.getStatus().name(), enrollment.getId());
    }

    public Enrollment findActiveEnrollment(int studentId, int courseId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE student_id = ? and course_id = ? and status = 'ACTIVE'";
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

    public Enrollment findByStudentAndCourse(int studentId, int courseId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
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

    public List<Enrollment> findByStudentId(int studentId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE student_id = ?";
        List<Enrollment> enrollments = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                enrollments.add(mapRow(rs));
            }
        }
        return enrollments;
    }

    public List<Enrollment> findByCourseId(int courseId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE course_id = ?";
        List<Enrollment> enrollments = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                enrollments.add(mapRow(rs));
            }
        }
        return enrollments;
    }
}
