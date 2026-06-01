package com.elearning.repository;

import com.elearning.model.CourseModule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseModuleRepository extends GenericRepository<CourseModule> {
    @Override
    protected String getFindByIdSql() {
        return "SELECT * FROM modules WHERE id = ?";
    }

    @Override
    protected String getFindAllSql() {
        return "SELECT * FROM modules";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM modules WHERE id = ?";
    }

    @Override
    protected CourseModule mapRow(ResultSet rs) throws SQLException {
        return new CourseModule(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("position"), // ?? si quizzez?
                rs.getInt("course_id")
        );
    }

    @Override
    public CourseModule save(CourseModule module) throws SQLException {
        String sql = "INSERT INTO modules (title, position, course_id) VALUES (?, ?, ?)";
        int id = executeInsert(sql, module.getTitle(), module.getPosition(), module.getCourseId());
        module.setId(id);
        return module;
    }

    @Override
    public void update(CourseModule module) throws SQLException {
        executeUpdate(
                "UPDATE modules SET title = ?, position = ? WHERE id = ?",
                module.getTitle(), module.getPosition(), module.getId()
        );
    }

    public List<CourseModule> findByCourseId(int courseId) throws SQLException {
        String sql = "SELECT * FROM modules WHERE course_id = ? ORDER BY position";
        List<CourseModule> modules = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) modules.add(mapRow(rs));
        }
        return modules;
    }
}
