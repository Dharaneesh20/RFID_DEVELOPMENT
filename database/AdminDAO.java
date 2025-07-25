package database;

import java.sql.*;
import models.Admin;

public class AdminDAO {
    public Admin getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();
                a.id = rs.getInt("id");
                a.username = rs.getString("username");
                a.passwordHash = rs.getString("password_hash");
                return a;
            }
        }
        return null;
    }
}
