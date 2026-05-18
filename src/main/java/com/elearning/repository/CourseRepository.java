package com.elearning.repository;

import com.elearning.enums.Difficulty;
import com.elearning.enums.Specialty;
import com.elearning.model.Course;
import com.elearning.model.Instructor;

import java.sql.*;

public class CourseRepository extends GenericRepository<Course> {
    // READ
    @Override
    protected String getFindByIdSql() {
        return "SELECT c.*, u.id AS user_main_id, u.name AS instructor_name, u.email, u.password, i.specialty " +
                "FROM courses c " +
                "JOIN instructors i ON c.instructor_id = i.user_id " +
                "JOIN users u ON i.user_id = u.id " +
                "WHERE c.id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT c.*, u.id AS user_main_id, u.name AS instructor_name, u.email, u.password, i.specialty " +
                "FROM courses c " +
                "JOIN instructors i ON c.instructor_id = i.user_id " +
                "JOIN users u ON i.user_id = u.id";
    }

    // DELETE
    @Override
    protected String getDeleteSql() {
        return "DELETE FROM courses WHERE id = ?";
    }

    @Override
    protected Course mapRow(ResultSet rs) throws SQLException {
        Instructor instructor = new Instructor(
                rs.getInt("user_main_id"),
                rs.getString("instructor_name"),
                rs.getString("email"),
                rs.getString("password"),
                Specialty.valueOf(rs.getString("specialty"))
        );
        return new Course(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                instructor,
                Difficulty.valueOf(rs.getString("difficulty")));
    }

    // CREATE
    public Course save(Course course) throws SQLException {
        String sql = "INSERT INTO courses (title, description, difficulty, instructor_id) VALUES (?, ?, ?, ?)";
        int id = executeInsert(sql, course.getTitle(), course.getDescription(),
                course.getDifficulty().name(), course.getInstructor().getId());
        course.setId(id);
        return course;
    }

    // UPDATE
    public void update(Course course) throws SQLException {
        String sql = "UPDATE courses SET title = ?, description = ?, difficulty = ? WHERE id = ?";
        executeUpdate(sql, course.getTitle(), course.getDescription(),
                course.getDifficulty().name(), course.getId());
    }

    // Specific method
    public Course findByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM courses c JOIN instructors i ON c.instructor_id = i.user_id "
                + "JOIN users u ON i.user_id = u.id WHERE LOWER(c.title) = LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }
}
