package com.LoanApplication.controller;

import com.LoanApplication.model.LoanApplicationModel;
import com.LoanApplication.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private LoanApplicationRepository loanRepository;

    @Autowired
    private JavaMailSender mailSender;

    // ----------------- Common Login for Admin/User -----------------

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/postLogin")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user/dashboard";
        }
        return "redirect:/login?error";
    }

    // ----------------- Admin Dashboard -----------------

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("applications", loanRepository.findAll());
        return "admin-dashboard";
    }

    // ✅ Admin View with Pagination & Sorting
    @GetMapping("/admin/applications")
    public String viewApplications(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "submittedAt") String sortBy,
                                   @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LoanApplicationModel> loanPage = loanRepository.findAll(pageable);

        model.addAttribute("loanPage", loanPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", loanPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "admin-applications";
    }

    // ✅ Update Status with optional rejection comment
    @PostMapping("/admin/updateStatus")
    public String updateLoanStatus(@RequestParam Long id,
                                   @RequestParam String status,
                                   @RequestParam(required = false) String comment) {
        Optional<LoanApplicationModel> optionalLoan = loanRepository.findById(id);

        if (optionalLoan.isPresent()) {
            LoanApplicationModel loan = optionalLoan.get();
            loan.setStatus(status);

            if ("REJECTED".equalsIgnoreCase(status)) {
                loan.setComment(comment);
            }

            loanRepository.save(loan);

            if (loan.getEmail() != null && !loan.getEmail().isEmpty()) {
                sendStatusEmail(loan.getEmail(), loan.getFullName(), status, comment);
            }
        }

        return "redirect:/admin/applications";
    }

    // ✅ Send email with optional rejection comment
    private void sendStatusEmail(String to, String fullName, String status, String comment) {
        String subject = "Loan Application Status Update";

        String body;
        if ("REJECTED".equalsIgnoreCase(status)) {
            body = "Dear " + fullName + ",\n\n"
                    + "Your loan application has been rejected.\n"
                    + "Reason: " + comment + "\n\n"
                    + "Best regards,\nLoan Management Team";
        } else {
            body = "Dear " + fullName + ",\n\n"
                    + "Your loan application has been approved.\n\n"
                    + "Best regards,\nLoan Management Team";
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("❌ Email failed to send to " + to);
            e.printStackTrace();
        }
    }

    // ----------------- Applicant Track Status Feature -----------------

    @GetMapping("/track")
    public String showTrackForm() {
        return "track-status";
    }

    @GetMapping("/track/status")
    public String trackStatus(@RequestParam("input") String input, Model model) {
        System.out.println("🔍 User Input: " + input);

        Optional<LoanApplicationModel> optionalApp = Optional.empty();

        if (input.matches("\\d+")) {
            optionalApp = loanRepository.findById(Long.parseLong(input));
        } else if (input.contains("@")) {
            optionalApp = loanRepository.findFirstByEmail(input);
        }

        if (optionalApp.isPresent()) {
            LoanApplicationModel application = optionalApp.get();
            System.out.println("✅ Found application: " + application);
            model.addAttribute("myapplication", application);
            model.addAttribute("notFound", false);
        } else {
            System.out.println("❌ No application found for input: " + input);
            model.addAttribute("notFound", true);
        }

        return "track-status";
    }
    
    @GetMapping("/admin/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        loanRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
    
    
}
