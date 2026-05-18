package com.elearning.repository;
import com.elearning.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericRepository<T> {
    protected Connection connection = DatabaseConnection.getInstance().getConnection();

    protected abstract T mapRow(ResultSet rs) throws SQLException;

    public abstract T save(T entity) throws SQLException;
    public abstract void update(T entity) throws SQLException;

    protected abstract String getFindByIdSql();
    protected abstract String getFindAllSql();
    protected abstract String getDeleteSql();

    public T findById(int id) throws SQLException{
        try (PreparedStatement stmt = connection.prepareStatement(getFindByIdSql())) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    public List<T> findAll() throws SQLException{
        List<T> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(getFindAllSql())) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
        }
        return results;
    }

    public void delete(int id) throws SQLException {
        executeUpdate(getDeleteSql(), id);
    }

    // helper functions for all repositories
    protected void executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        }
    }

    protected int executeInsert(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // pentru a recupera id-ul
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
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