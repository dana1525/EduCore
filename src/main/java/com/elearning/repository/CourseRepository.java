package com.elearning.repository;

import com.elearning.enums.Difficulty;
import com.elearning.model.Course;
import com.elearning.model.Instructor;

import java.sql.*;

public class CourseRepository extends GenericRepository<Course> {
    private final InstructorRepository instructorRepository = new InstructorRepository();

    // READ
    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM courses WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM courses";
    }

    // DELETE
    @Override
    protected String getDeleteSql() {
        return "DELETE FROM courses WHERE id = ?";
    }

    @Override
    protected Course mapRow(ResultSet rs) throws SQLException {
        int instructorId = rs.getInt("instructor_id");
        Instructor instructor = instructorRepository.findById(instructorId);
        return new Course(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                instructor,
                Difficulty.valueOf(rs.getString("difficulty"))
        );
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
        String sql = "UPDATE courses SET title = ?, description = ?, difficulty = ?, instructor_id = ? WHERE id = ?";
        executeUpdate(sql, course.getTitle(), course.getDescription(),
                course.getDifficulty().name(), course.getInstructor().getId(), course.getId());
    }

    // Specific method
    public Course findByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM courses WHERE LOWER(title) = LOWER(?)";
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
