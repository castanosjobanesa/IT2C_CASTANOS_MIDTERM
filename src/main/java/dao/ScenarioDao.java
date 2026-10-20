package com.scc.dao;

import com.scc.model.ScenarioEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS training_sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "client_name TEXT NOT NULL, " +
                "trainer_name TEXT NOT NULL, " +
                "session_fee REAL NOT NULL, " +
                "status TEXT NOT NULL)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    public void addSession(String clientName, String trainerName, double sessionFee, String status) {
        String sql = "INSERT INTO training_sessions (client_name, trainer_name, session_fee, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientName);
            pstmt.setString(2, trainerName);
            pstmt.setDouble(3, sessionFee);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding session: " + e.getMessage());
        }
    }

    public List<ScenarioEntity> getAllSessions() {
        List<ScenarioEntity> sessions = new ArrayList<>();
        String sql = "SELECT id, client_name, trainer_name, session_fee, status FROM training_sessions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sessions.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("client_name"),
                        rs.getString("trainer_name"),
                        rs.getDouble("session_fee"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sessions: " + e.getMessage());
        }
        return sessions;
    }
}