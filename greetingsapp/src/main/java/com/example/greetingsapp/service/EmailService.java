package com.example.greetingsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

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
}
