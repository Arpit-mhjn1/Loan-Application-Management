package com.LoanApplication.repository;

import com.LoanApplication.model.LoanApplicationModel;
import com.LoanApplication.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationModel, Long> {

    // ✅ Get the latest loan application by email

    // ✅ Get all loans submitted by a user (for dashboard)
	Optional<LoanApplicationModel> findFirstByEmail(String email);
    List<LoanApplicationModel> findByUser(User user);
    List<LoanApplicationModel> findByStatus(String status);

    // (Optional) Filter by status if needed
}
