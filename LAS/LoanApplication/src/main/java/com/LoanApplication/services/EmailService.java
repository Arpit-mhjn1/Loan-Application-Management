package com.LoanApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendStatusEmail(String toEmail, String fullName, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Loan Application Status");
        message.setText("Dear " + fullName + ",\n\nYour loan application has been " + status + ".\n\nRegards,\nLoan App Team");
        message.setFrom("your_email@gmail.com");

        mailSender.send(message);
    }
}
