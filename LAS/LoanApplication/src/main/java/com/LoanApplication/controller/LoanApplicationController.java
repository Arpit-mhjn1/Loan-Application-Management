package com.LoanApplication.controller;

import com.LoanApplication.model.LoanApplicationModel;
import com.LoanApplication.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoanApplicationController {

    @Autowired
    private LoanApplicationRepository loanRepo;

    // Used only for API (if needed)
    @ResponseBody
    @PostMapping("/api/loan/apply")
    public LoanApplicationModel applyLoan(@RequestBody LoanApplicationModel loan) {
        return loanRepo.save(loan);
    }

    // Used only for API (if needed)
    @ResponseBody
    @GetMapping("/api/loan/all")
    public List<LoanApplicationModel> getAllLoans() {
        return loanRepo.findAll();
    }

}
