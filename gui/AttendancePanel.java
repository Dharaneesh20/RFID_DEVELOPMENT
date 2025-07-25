package gui;

import database.AttendanceDAO;
import database.UserDAO;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import models.Attendance;
import models.User;

public class AttendancePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private UserDAO userDAO = new UserDAO();

    public AttendancePanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"Card #", "Name", "Status", "Login", "Logout"}, 0);
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void refreshTable() {
        model.setRowCount(0);
        try {
            Map<Integer, User> userMap = new HashMap<>();
            for (User u : userDAO.getAll()) {
                userMap.put(u.id, u);
            }
            List<Attendance> list = attendanceDAO.getTodayAttendance(LocalDate.now());
            for (Attendance a : list) {
                User u = userMap.get(a.userId);
                model.addRow(new Object[]{
                    u.cardNumber,
                    u.firstName + " " + u.lastName,
                    a.status,
                    a.loginTime != null ? a.loginTime.toString() : "",
                    a.logoutTime != null ? a.logoutTime.toString() : ""
                });
            }
        } catch (Exception e) {
            // handle error
        }
    }
}
