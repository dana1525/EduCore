package com.elearning.repository;

import com.elearning.enums.Specialty;
import com.elearning.model.Instructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorRepository extends GenericRepository<Instructor> {
    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM users join instructors on id = user_id WHERE users.id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM users join instructors on users.id = instructors.user_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM users WHERE id = ?"; // CASCADE delete from instructors automatically
    }

    @Override
    protected Instructor mapRow(ResultSet rs) throws SQLException {
        return new Instructor(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                Specialty.valueOf(rs.getString("specialty"))
        );
    }

    @Override
    public Instructor save(Instructor instructor) throws SQLException {
        String sqlUser = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        String sqlInstructor = "INSERT INTO instructors (user_id, specialty) VALUES (?, ?)";
        connection.setAutoCommit(false);
        try {
            int generatedId = executeInsert(sqlUser, instructor.getName(),
                    instructor.getEmail(), instructor.getPassword(), instructor.getRole());
            executeInsert(sqlInstructor, generatedId, instructor.getSpecialty().name());
            connection.commit();
            instructor.setId(generatedId);
            return instructor;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void update(Instructor instructor) throws SQLException {
        String sqlUser = "UPDATE users SET name = ?, email = ?, password = ? WHERE users.id = ?";
        String sqlInstructor = "UPDATE instructors SET specialty = ? WHERE user_id = ?";

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sqlUser)) {
                stmt.setString(1, instructor.getName());
                stmt.setString(2, instructor.getEmail());
                stmt.setString(3, instructor.getPassword());
                stmt.setInt(4, instructor.getId());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlInstructor)) {
                stmt.setString(1, instructor.getSpecialty().name());
                stmt.setInt(2, instructor.getId());
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

    public Instructor findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users join instructors on users.id = instructors.user_id where email = ?";
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
