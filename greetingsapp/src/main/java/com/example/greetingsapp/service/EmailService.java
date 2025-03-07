package com.example.greetingsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String toEmail, String firstName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to GreetingsApp");
        message.setText("Hi " + firstName + ",\n\nWelcome to GreetingsApp! We're excited to have you onboard.\n\nThanks & Regards,\nGreetingsApp Team");

        mailSender.send(message);
    }

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendPasswordChangeNotification(String email) {
        String subject = "Password Changed Successfully";
        String message = "Hello, your password has been updated successfully. If you didn't request this change, please contact support immediately.";
        sendEmail(email, subject, message);
    }
}
