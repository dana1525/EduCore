package com.elearning.repository;
import com.elearning.model.Student;

import java.sql.*;

public class StudentRepository extends GenericRepository<Student> {
    // READ
    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM users join students on id = user_id WHERE users.id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM users join students on users.id = students.user_id";
    }

    // DELETE
    @Override
    protected String getDeleteSql() {
        return "DELETE FROM users WHERE id = ?"; // CASCADE delete from students automatically
    }

    @Override
    protected Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDouble("progress")
        );
    }

    // CREATE (transaction - 2 tabels)
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

    // UPDATE (transaction - 2 tabels)
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
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Specific method
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
}
