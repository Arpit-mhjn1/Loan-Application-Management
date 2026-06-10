package model;

import java.sql.Date;

public class LoanApplication {
    private int id;
    private int userId;
    private String fullName;
    private Date dob;
    private String panAadhar;
    private String loanType;
    private double loanAmount;
    private double monthlyIncome;
    private String employmentType;
    private String reason;
    private String idProofPath;
    private String incomeProofPath;
    private String status;
    private String adminComments;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    public String getPanAadhar() { return panAadhar; }
    public void setPanAadhar(String panAadhar) { this.panAadhar = panAadhar; }
    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }
    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
    public double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getIdProofPath() { return idProofPath; }
    public void setIdProofPath(String idProofPath) { this.idProofPath = idProofPath; }
    public String getIncomeProofPath() { return incomeProofPath; }
    public void setIncomeProofPath(String incomeProofPath) { this.incomeProofPath = incomeProofPath; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAdminComments() { return adminComments; }
    public void setAdminComments(String adminComments) { this.adminComments = adminComments; }
}
