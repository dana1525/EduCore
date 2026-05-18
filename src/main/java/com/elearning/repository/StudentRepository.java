package com.elearning.repository;
import com.elearning.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends GenericRepository<Student> {
    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDouble("progress")
        );
    };

    // CREATE
    public Student save(Student student) throws SQLException {
        String sqlUser = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        String sqlStudent = "INSERT INTO students (user_id, progress) VALUES (?, ?)";
        connection.setAutoCommit(false);
        try {
            int generatedId = executeInsert(sqlUser, student.getName(),
                    student.getEmail(), student.getPassword(), student.getRole());
            executeInsert(sqlStudent, generatedId, student.getProgress());
            connection.commit();
            student.setId(generatedId);
            return student;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // READ
    public Student findById(int id) throws SQLException {
        String sql = "SELECT * FROM users join students on id = user_id where users.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    public Student findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users join students on users.id = students.user_id where email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM users join students on users.id = students.user_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(mapRow(rs));
            }
        }
        return students;
    }

    // UPDATE (transaction in this method)
    public void update(Student student) throws SQLException {
        String sqlUser = "UPDATE users SET name = ?, email = ?, password = ? WHERE users.id = ?";
        String sqlStudent = "UPDATE students SET progress = ? WHERE user_id = ?";

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sqlUser)) {
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getEmail());
                stmt.setString(3, student.getPassword());
                stmt.setInt(4, student.getId());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlStudent)) {
                stmt.setDouble(1, student.getProgress());
                stmt.setInt(2, student.getId());
                stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("Failed to update student.", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // DELETE
    public void delete(int id) {
        executeUpdate("DELETE FROM users WHERE id = ?", id);
    }
}
