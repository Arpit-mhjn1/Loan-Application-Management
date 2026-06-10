package controller;

import dao.LoanApplicationDAO;
import model.LoanApplication;
import java.util.List;

public class LoanController {
    private LoanApplicationDAO loanDAO;

    public LoanController() {
        this.loanDAO = new LoanApplicationDAO();
    }

    public boolean submitApplication(LoanApplication app) {
        return loanDAO.submitApplication(app);
    }

    public List<LoanApplication> getUserApplications(int userId) {
        return loanDAO.getApplicationsByUserId(userId);
    }

    public List<LoanApplication> getAllApplications() {
        return loanDAO.getAllApplications();
    }

    public boolean reviewApplication(int appId, String status, String comments) {
        return loanDAO.updateApplicationStatus(appId, status, comments);
    }
}
