package dao;

import model.LoanApplication;
import utility.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanApplicationDAO {

    public boolean submitApplication(LoanApplication app) {
        String query = "INSERT INTO loan_applications (user_id, full_name, dob, pan_aadhar, loan_type, loan_amount, monthly_income, employment_type, reason, id_proof_path, income_proof_path, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, app.getUserId());
            stmt.setString(2, app.getFullName());
            stmt.setDate(3, app.getDob());
            stmt.setString(4, app.getPanAadhar());
            stmt.setString(5, app.getLoanType());
            stmt.setDouble(6, app.getLoanAmount());
            stmt.setDouble(7, app.getMonthlyIncome());
            stmt.setString(8, app.getEmploymentType());
            stmt.setString(9, app.getReason());
            stmt.setString(10, app.getIdProofPath());
            stmt.setString(11, app.getIncomeProofPath());
            stmt.setString(12, app.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LoanApplication> getApplicationsByUserId(int userId) {
        List<LoanApplication> list = new ArrayList<>();
        String query = "SELECT * FROM loan_applications WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(extractAppFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoanApplication> getAllApplications() {
        List<LoanApplication> list = new ArrayList<>();
        String query = "SELECT * FROM loan_applications";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(extractAppFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateApplicationStatus(int appId, String status, String comments) {
        String query = "UPDATE loan_applications SET status = ?, admin_comments = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, comments);
            stmt.setInt(3, appId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private LoanApplication extractAppFromResultSet(ResultSet rs) throws SQLException {
        LoanApplication app = new LoanApplication();
        app.setId(rs.getInt("id"));
        app.setUserId(rs.getInt("user_id"));
        app.setFullName(rs.getString("full_name"));
        app.setDob(rs.getDate("dob"));
        app.setPanAadhar(rs.getString("pan_aadhar"));
        app.setLoanType(rs.getString("loan_type"));
        app.setLoanAmount(rs.getDouble("loan_amount"));
        app.setMonthlyIncome(rs.getDouble("monthly_income"));
        app.setEmploymentType(rs.getString("employment_type"));
        app.setReason(rs.getString("reason"));
        app.setIdProofPath(rs.getString("id_proof_path"));
        app.setIncomeProofPath(rs.getString("income_proof_path"));
        app.setStatus(rs.getString("status"));
        app.setAdminComments(rs.getString("admin_comments"));
        return app;
    }
}
