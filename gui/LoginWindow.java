package gui;

import java.awt.*;
import javax.swing.*;
import services.AuthService;

public class LoginWindow extends JFrame {
    public LoginWindow() {
        setTitle("Admin Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,2));
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JLabel msgLabel = new JLabel();

        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);
        panel.add(new JLabel()); panel.add(loginBtn);

        add(panel, BorderLayout.CENTER);
        add(msgLabel, BorderLayout.SOUTH);

        AuthService authService = new AuthService();

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (authService.authenticate(user, pass)) {
                dispose();
                new DashboardWindow().setVisible(true);
            } else {
                msgLabel.setText("Invalid credentials.");
            }
        });
    }
}
