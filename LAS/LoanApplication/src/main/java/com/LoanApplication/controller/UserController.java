package com.LoanApplication.controller;

import com.LoanApplication.model.LoanApplicationModel;
import com.LoanApplication.model.User;
import com.LoanApplication.repository.LoanApplicationRepository;
import com.LoanApplication.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Show user registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user_register";
    }

    // ✅ Handle user registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user_register";
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ✅ Save role as USER/ADMIN only (NOT with ROLE_ prefix)
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        } else {
            user.setRole(user.getRole().toUpperCase().replace("ROLE_", ""));
        }

        userRepository.save(user);
        return "redirect:/register-success";
    }

    // ✅ Success page after registration
    @GetMapping("/register-success")
    public String successPage() {
        return "register_success";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<LoanApplicationModel> loans = loanRepo.findByUser(user);

            // ✅ Debug log
            System.out.println("📌 Loans fetched for user: " + loans.size());
            for (LoanApplicationModel loan : loans) {
                System.out.println("🔹 Loan ID: " + loan.getId() + ", Email: " + loan.getEmail());
            }

            model.addAttribute("user", user);
            model.addAttribute("loans", loans);
        } else {
            System.out.println("❌ User not found for email: " + email);
        }

        return "user-dashboard";
    }
    
    @GetMapping("/loan/delete/{id}")
    public String deleteUserLoan(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Optional<LoanApplicationModel> loanOpt = loanRepo.findById(id);
            if (loanOpt.isPresent()) {
                LoanApplicationModel loan = loanOpt.get();
                if (loan.getUser().getId().equals(userOpt.get().getId())) {
                    loanRepo.delete(loan);
                    System.out.println("✅ Loan ID " + id + " deleted successfully.");
                } else {
                    System.out.println("❌ Unauthorized deletion attempt.");
                }
            }
        }

        return "redirect:/user/dashboard";
    }

    
    
    

}
