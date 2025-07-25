package database;

import models.User;
import java.sql.*;
import java.util.*;

public class UserDAO {
    public User getByCardNumber(String cardNumber) throws SQLException {
        String sql = "SELECT * FROM users WHERE card_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        }
        return null;
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(map(rs));
            }
        }
        return users;
    }

    public void add(User user) throws SQLException {
        String sql = "INSERT INTO users (card_number, first_name, last_name, email, phone, department, role, enabled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.cardNumber);
            stmt.setString(2, user.firstName);
            stmt.setString(3, user.lastName);
            stmt.setString(4, user.email);
            stmt.setString(5, user.phone);
            stmt.setString(6, user.department);
            stmt.setString(7, user.role);
            stmt.setBoolean(8, user.enabled);
            stmt.executeUpdate();
        }
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET first_name=?, last_name=?, email=?, phone=?, department=?, role=?, enabled=? WHERE card_number=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.firstName);
            stmt.setString(2, user.lastName);
            stmt.setString(3, user.email);
            stmt.setString(4, user.phone);
            stmt.setString(5, user.department);
            stmt.setString(6, user.role);
            stmt.setBoolean(7, user.enabled);
            stmt.setString(8, user.cardNumber);
            stmt.executeUpdate();
        }
    }

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.id = rs.getInt("id");
        u.cardNumber = rs.getString("card_number");
        u.firstName = rs.getString("first_name");
        u.lastName = rs.getString("last_name");
        u.email = rs.getString("email");
        u.phone = rs.getString("phone");
        u.department = rs.getString("department");
        u.role = rs.getString("role");
        u.enabled = rs.getBoolean("enabled");
        return u;
    }
}