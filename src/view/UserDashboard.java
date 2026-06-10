package view;

import controller.LoanController;
import model.LoanApplication;
import model.User;
import utility.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class UserDashboard extends JFrame {
    private User currentUser;
    private LoanController loanController;
    private JPanel mainPanel;

    public UserDashboard(User user) {
        this.currentUser = user;
        this.loanController = new LoanController();
        setTitle("User Dashboard - Welcome " + user.getFullName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem itemNew = new JMenuItem("New Application");
        JMenuItem itemMy = new JMenuItem("My Applications");
        JMenuItem itemLogout = new JMenuItem("Logout");

        menu.add(itemNew);
        menu.add(itemMy);
        menu.addSeparator();
        menu.add(itemLogout);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        mainPanel = new JPanel();
        add(mainPanel);

        itemNew.addActionListener(e -> showNewApplicationForm());
        itemMy.addActionListener(e -> showMyApplications());
        itemLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        showMyApplications();
    }

    private void showMyApplications() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        
        List<LoanApplication> apps = loanController.getUserApplications(currentUser.getId());
        String[] columns = {"ID", "Loan Type", "Amount", "Status"};
        String[][] data = new String[apps.size()][4];
        
        for (int i = 0; i < apps.size(); i++) {
            data[i][0] = String.valueOf(apps.get(i).getId());
            data[i][1] = apps.get(i).getLoanType();
            data[i][2] = String.valueOf(apps.get(i).getLoanAmount());
            data[i][3] = apps.get(i).getStatus();
        }

        JTable table = new JTable(data, columns);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showNewApplicationForm() {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(10, 2, 10, 10));

        JTextField amountField = new JTextField();
        JTextField incomeField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Personal", "Home", "Education", "Business"});
        JTextField aadharField = new JTextField();
        
        JButton idUploadBtn = new JButton("Upload ID (PDF/JPG)");
        JButton incomeUploadBtn = new JButton("Upload Income Proof");
        JLabel idFileLabel = new JLabel("No file selected");
        JLabel incomeFileLabel = new JLabel("No file selected");
        
        final String[] idPath = new String[1];
        final String[] incomePath = new String[1];

        idUploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (ValidationUtil.isValidFile(file)) {
                    idPath[0] = file.getAbsolutePath();
                    idFileLabel.setText(file.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid file! Must be PDF/JPG under 2MB.");
                }
            }
        });

        incomeUploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (ValidationUtil.isValidFile(file)) {
                    incomePath[0] = file.getAbsolutePath();
                    incomeFileLabel.setText(file.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid file! Must be PDF/JPG under 2MB.");
                }
            }
        });

        mainPanel.add(new JLabel("Loan Amount:")); mainPanel.add(amountField);
        mainPanel.add(new JLabel("Monthly Income:")); mainPanel.add(incomeField);
        mainPanel.add(new JLabel("Loan Type:")); mainPanel.add(typeBox);
        mainPanel.add(new JLabel("PAN/Aadhar:")); mainPanel.add(aadharField);
        mainPanel.add(idUploadBtn); mainPanel.add(idFileLabel);
        mainPanel.add(incomeUploadBtn); mainPanel.add(incomeFileLabel);

        JButton submitBtn = new JButton("Submit Application");
        mainPanel.add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                double inc = Double.parseDouble(incomeField.getText());
                
                if (!ValidationUtil.isValidLoanAmount(amt, inc)) {
                    JOptionPane.showMessageDialog(this, "Loan amount cannot exceed 20x monthly income.");
                    return;
                }
                
                LoanApplication app = new LoanApplication();
                app.setUserId(currentUser.getId());
                app.setFullName(currentUser.getFullName());
                app.setDob(Date.valueOf(LocalDate.of(2000, 1, 1))); // Mock DOB
                app.setLoanAmount(amt);
                app.setMonthlyIncome(inc);
                app.setLoanType((String) typeBox.getSelectedItem());
                app.setPanAadhar(aadharField.getText());
                app.setIdProofPath(idPath[0]);
                app.setIncomeProofPath(incomePath[0]);
                app.setStatus("PENDING");
                
                if (loanController.submitApplication(app)) {
                    JOptionPane.showMessageDialog(this, "Application Submitted!");
                    showMyApplications();
                } else {
                    JOptionPane.showMessageDialog(this, "Error submitting application.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid data.");
            }
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
