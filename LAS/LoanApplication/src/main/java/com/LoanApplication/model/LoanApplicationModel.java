package com.LoanApplication.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplicationModel {

    public LoanApplicationModel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(name = "document_path")
    private String documentPath;

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
    
    
    private String fullName;
    private String dob;
    private String panNumber;
    private String aadharNumber;
    private String loanType; // Personal, Home, Business etc.
    private Double loanAmount;
    private Double monthlyIncome;
    private String employmentType;
    private String reason;
    
    private String comment;
    
    

    private String status = "PENDING";  // Default status

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }

	public LoanApplicationModel(Long id, String email, String fullName, String dob, String panNumber,
			String aadharNumber, String loanType, Double loanAmount, Double monthlyIncome, String employmentType,
			String reason, String status, LocalDateTime submittedAt, User user) {
		super();
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.dob = dob;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.monthlyIncome = monthlyIncome;
		this.employmentType = employmentType;
		this.reason = reason;
		this.status = status;
		this.submittedAt = submittedAt;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoanApplicationModel [id=" + id + ", email=" + email + ", fullName=" + fullName + ", dob=" + dob
				+ ", panNumber=" + panNumber + ", aadharNumber=" + aadharNumber + ", loanType=" + loanType
				+ ", loanAmount=" + loanAmount + ", monthlyIncome=" + monthlyIncome + ", employmentType="
				+ employmentType + ", reason=" + reason + ", status=" + status + ", submittedAt=" + submittedAt
				+ ", user=" + user + "]";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LoanApplicationModel(Long id, String email, String fullName, String dob, String panNumber,
			String aadharNumber, String loanType, Double loanAmount, Double monthlyIncome, String employmentType,
			String reason, String comment, String status, LocalDateTime submittedAt, User user) {
		super();
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.dob = dob;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.monthlyIncome = monthlyIncome;
		this.employmentType = employmentType;
		this.reason = reason;
		this.comment = comment;
		this.status = status;
		this.submittedAt = submittedAt;
		this.user = user;
	}
    
    

    
}
