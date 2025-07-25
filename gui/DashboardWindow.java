package gui;

import java.awt.*;
import javax.swing.*;

public class DashboardWindow extends JFrame {
    public DashboardWindow() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Users", new UserManagementPanel());
        tabs.addTab("Attendance", new AttendancePanel());
        // ...add more tabs as needed...

        add(tabs, BorderLayout.CENTER);
    }
}
