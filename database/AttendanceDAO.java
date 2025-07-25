package database;

import java.sql.*;
import java.time.*;
import java.util.*;
import models.Attendance;

public class AttendanceDAO {
    public void markLogin(int userId, LocalDate date, LocalDateTime loginTime) throws SQLException {
        String sql = "INSERT INTO attendance (user_id, date, login_time, status) VALUES (?, ?, ?, 'present') " +
                     "ON CONFLICT (user_id, date) DO UPDATE SET login_time = EXCLUDED.login_time, status='present'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setTimestamp(3, Timestamp.valueOf(loginTime));
            stmt.executeUpdate();
        }
    }

    public void markLogout(int userId, LocalDate date, LocalDateTime logoutTime) throws SQLException {
        String sql = "UPDATE attendance SET logout_time=?, status='partial' WHERE user_id=? AND date=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(logoutTime));
            stmt.setInt(2, userId);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.executeUpdate();
        }
    }

    public List<Attendance> getTodayAttendance(LocalDate date) throws SQLException {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE date=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.id = rs.getInt("id");
                a.userId = rs.getInt("user_id");
                a.date = rs.getDate("date").toLocalDate();
                Timestamp loginTs = rs.getTimestamp("login_time");
                a.loginTime = loginTs != null ? loginTs.toLocalDateTime() : null;
                Timestamp logoutTs = rs.getTimestamp("logout_time");
                a.logoutTime = logoutTs != null ? logoutTs.toLocalDateTime() : null;
                a.status = rs.getString("status");
                list.add(a);
            }
        }
        return list;
    }
}
