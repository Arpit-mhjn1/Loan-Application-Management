package view;

import controller.AuthController;
import model.User;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private AuthController authController;

    public LoginFrame() {
        authController = new AuthController();
        setTitle("Loan Application System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("  Email/Admin User:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("  Password:"));
        passField = new JPasswordField();
        add(passField);

        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");
        JButton adminLoginBtn = new JButton("Admin Login");

        add(loginBtn);
        add(regBtn);
        add(adminLoginBtn);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            User user = authController.login(email, pass);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new UserDashboard(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });

        adminLoginBtn.addActionListener(e -> {
            String username = emailField.getText();
            String pass = new String(passField.getPassword());
            if (authController.adminLogin(username, pass)) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                new AdminDashboard().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials");
            }
        });

        regBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            this.dispose();
        });
    }
}
