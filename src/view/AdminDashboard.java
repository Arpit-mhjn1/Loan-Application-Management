package view;

import controller.LoanController;
import model.LoanApplication;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private LoanController loanController;
    private JPanel mainPanel;

    public AdminDashboard() {
        this.loanController = new LoanController();
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Admin Menu");
        JMenuItem itemAll = new JMenuItem("All Applications");
        JMenuItem itemLogout = new JMenuItem("Logout");

        menu.add(itemAll);
        menu.addSeparator();
        menu.add(itemLogout);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        mainPanel = new JPanel();
        add(mainPanel);

        itemAll.addActionListener(e -> showAllApplications());
        itemLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        showAllApplications();
    }

    private void showAllApplications() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        List<LoanApplication> apps = loanController.getAllApplications();
        String[] columns = {"ID", "User ID", "Name", "Type", "Amount", "Status"};
        String[][] data = new String[apps.size()][6];

        for (int i = 0; i < apps.size(); i++) {
            data[i][0] = String.valueOf(apps.get(i).getId());
            data[i][1] = String.valueOf(apps.get(i).getUserId());
            data[i][2] = apps.get(i).getFullName();
            data[i][3] = apps.get(i).getLoanType();
            data[i][4] = String.valueOf(apps.get(i).getLoanAmount());
            data[i][5] = apps.get(i).getStatus();
        }

        JTable table = new JTable(data, columns);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        JTextField idField = new JTextField(5);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"APPROVED", "REJECTED"});
        JTextField commentField = new JTextField(15);
        JButton updateBtn = new JButton("Update Status");

        actionPanel.add(new JLabel("App ID:")); actionPanel.add(idField);
        actionPanel.add(new JLabel("Status:")); actionPanel.add(statusBox);
        actionPanel.add(new JLabel("Comments:")); actionPanel.add(commentField);
        actionPanel.add(updateBtn);

        updateBtn.addActionListener(e -> {
            try {
                int appId = Integer.parseInt(idField.getText());
                String status = (String) statusBox.getSelectedItem();
                String comments = commentField.getText();

                if (loanController.reviewApplication(appId, status, comments)) {
                    JOptionPane.showMessageDialog(this, "Updated successfully!");
                    showAllApplications();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
        });

        mainPanel.add(actionPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
