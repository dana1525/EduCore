package com.elearning.repository;
import com.elearning.config.DatabaseConnection;

import java.sql.*;
import java.util.List;

public abstract class GenericRepository<T> {
    protected Connection connection = DatabaseConnection.getInstance().getConnection();

    public abstract T save(T entity) throws SQLException;
    public abstract T findById(int id) throws SQLException;
    public abstract List<T> findAll() throws SQLException;
    public abstract void update(T entity) throws SQLException;
    public abstract void delete(int id) throws SQLException;

    // helper functions for all repositories
    protected void executeUpdate(String sql, Object... params){
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    protected int executeInsert(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // pentru a recupera id-ul
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i, params[i]);
            }
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new RuntimeException("Failed to retrieve generated ID.");
        }
    }
}