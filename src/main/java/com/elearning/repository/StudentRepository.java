package com.elearning.repository;

import com.elearning.config.DatabaseConnection;
import com.elearning.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    // CREATE
    public Student save(Student student) throws SQLException {
        String sqlUser = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        String sqlStudent = "INSERT INTO students (user_id, progress) VALUES (?, ?)";
        try {
            connection.setAutoCommit(false);
            int generatedId;

            try (PreparedStatement stmt = connection.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) { // pentru a recupera id-ul
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getEmail());
                stmt.setString(3, student.getPassword());
                stmt.setString(4, student.getRole());
                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                keys.next();
                generatedId = keys.getInt(1);
            }
            try(PreparedStatement stmt = connection.prepareStatement(sqlStudent)) {
                stmt.setInt(1, generatedId);
                stmt.setDouble(2, student.getProgress());
                stmt.executeUpdate();
            }
            connection.commit();
            return new Student(generatedId, student.getName(), student.getEmail(), student.getPassword(), student.getProgress());
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("Failed to save student.", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // READ
    public Student findById(int id) {
        String sql = "SELECT * FROM users join students on id = user_id where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password"), rs.getDouble("progress"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student findByEmail(String email) {
        String sql = "SELECT * FROM users join students on id = user_id where email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password"), rs.getDouble("progress"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM users join students on id = user_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password"), rs.getDouble("progress"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing the students.", e);
        }
        return students;
    }

    // UPDATE
    public void update(Student student) throws SQLException {
        String sqlUser = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
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
    public void delete(int id) throws SQLException {
        String sqlUser = "DELETE FROM users WHERE id = ?";
        String sqlStudent = "DELETE FROM students WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(sqlStudent)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlUser)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("Failed to delete student.", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
