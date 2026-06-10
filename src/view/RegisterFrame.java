package view;

import controller.AuthController;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passField;
    private AuthController authController;

    public RegisterFrame() {
        authController = new AuthController();
        setTitle("Loan Application System - Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("  Full Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("  Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("  Password:"));
        passField = new JPasswordField();
        add(passField);

        JButton regBtn = new JButton("Register");
        JButton backBtn = new JButton("Back to Login");

        add(regBtn);
        add(backBtn);

        regBtn.addActionListener(e -> {
            if (authController.register(nameField.getText(), emailField.getText(), new String(passField.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please login.");
                new LoginFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!");
            }
        });

        backBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
}
