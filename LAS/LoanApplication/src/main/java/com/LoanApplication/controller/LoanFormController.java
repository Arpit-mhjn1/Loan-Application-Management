package com.LoanApplication.controller;

import com.LoanApplication.model.LoanApplicationModel;
import com.LoanApplication.model.User;
import com.LoanApplication.repository.LoanApplicationRepository;
import com.LoanApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class LoanFormController {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Show loan form
    @GetMapping("/loan/form")
    @PreAuthorize("hasRole('USER')")
    public String showForm(Model model) {
        model.addAttribute("loan", new LoanApplicationModel());
        return "loan-form";
    }

    // Submit loan form (no file upload)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/loan/submit")
    public String submitForm(@ModelAttribute LoanApplicationModel loan,
                             Authentication authentication) throws IOException {

        String email = authentication.getName();
        Optional<User> userOpt = userRepo.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            loan.setUser(user);
            loan.setEmail(user.getEmail());
            loan.setFullName(user.getFirstName() + " " + user.getLastName());
            loan.setStatus("PENDING");

            LoanApplicationModel savedLoan = loanRepo.save(loan);

            // Send email confirmation
            if (savedLoan.getEmail() != null && !savedLoan.getEmail().isEmpty()) {
                sendConfirmationEmail(savedLoan);
            }

            return "redirect:/loan/success?id=" + savedLoan.getId();
        }

        return "redirect:/loan/form?error=userNotFound";
    }

    @GetMapping("/loan/success")
    public String successPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("applicationId", id);
        return "success";
    }

    private void sendConfirmationEmail(LoanApplicationModel loan) {
        String to = loan.getEmail();
        String subject = "Loan Application Submitted Successfully";
        String body = "Dear " + loan.getFullName() + ",\n\n"
                + "Thank you for applying for a loan. Your application has been received.\n"
                + "Your Application ID is: " + loan.getId() + "\n\n"
                + "You can track your status using this ID or your email on our portal.\n\n"
                + "Best regards,\nLoan Management Team";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("❌ Failed to send confirmation email to " + to);
            e.printStackTrace();
        }
    }

    @GetMapping("/loan/all")
    public String showAllLoans(Model model) {
        List<LoanApplicationModel> loans = loanRepo.findAll();
        model.addAttribute("loans", loans);
        return "all-loans";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }
}
