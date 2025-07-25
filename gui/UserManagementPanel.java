package gui;

import database.UserDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import models.User;

public class UserManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private UserDAO userDAO = new UserDAO();

    public UserManagementPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"Card #", "First", "Last", "Email", "Phone", "Dept", "Role", "Enabled"}, 0);
        table = new JTable(model);
        refreshTable();

        JButton addBtn = new JButton("Add User");
        addBtn.addActionListener(e -> showAddDialog());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(addBtn, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        model.setRowCount(0);
        try {
            List<User> users = userDAO.getAll();
            for (User u : users) {
                model.addRow(new Object[]{
                    u.cardNumber, u.firstName, u.lastName, u.email, u.phone, u.department, u.role, u.enabled
                });
            }
        } catch (Exception e) {
            // handle error
        }
    }

    private void showAddDialog() {
        JTextField cardField = new JTextField();
        JTextField firstField = new JTextField();
        JTextField lastField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField deptField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"user", "admin"});

        JPanel panel = new JPanel(new GridLayout(7,2));
        panel.add(new JLabel("Card #:")); panel.add(cardField);
        panel.add(new JLabel("First Name:")); panel.add(firstField);
        panel.add(new JLabel("Last Name:")); panel.add(lastField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Department:")); panel.add(deptField);
        panel.add(new JLabel("Role:")); panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            User u = new User();
            u.cardNumber = cardField.getText().trim();
            u.firstName = firstField.getText().trim();
            u.lastName = lastField.getText().trim();
            u.email = emailField.getText().trim();
            u.phone = phoneField.getText().trim();
            u.department = deptField.getText().trim();
            u.role = (String)roleBox.getSelectedItem();
            u.enabled = true;
            try {
                userDAO.add(u);
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage());
            }
        }
    }
}
