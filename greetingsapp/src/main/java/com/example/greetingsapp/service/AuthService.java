package com.example.greetingsapp.service;

import com.example.greetingsapp.dto.AuthUserDTO;
import com.example.greetingsapp.dto.LoginDTO;
import com.example.greetingsapp.exception.EmailAlreadyExistsException;
import com.example.greetingsapp.exception.InvalidLoginException;
import com.example.greetingsapp.model.AuthUser;
import com.example.greetingsapp.repository.AuthUserRepository;
import com.example.greetingsapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    AuthUserRepository repository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;
    EmailService emailService;

    @Autowired
    public AuthService(AuthUserRepository repository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Override
    public String registerUser(AuthUserDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + dto.getEmail());
        }

        AuthUser user = new AuthUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        repository.save(user);
        emailService.sendWelcomeEmail(user.getEmail(), user.getFirstName());

        return "User registered successfully! Welcome email sent.";
    }

    @Override
    public String loginUser(LoginDTO dto) {
        AuthUser user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidLoginException("Invalid email or password!"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidLoginException("Invalid email or password!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email, String newPassword) {
        Optional<AuthUser> optionalUser = repository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Sorry! We cannot find the user email: " + email));
        }

        AuthUser user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        emailService.sendPasswordChangeNotification(email);

        return ResponseEntity.ok(Map.of("message", "Password has been changed successfully!"));
    }

    @Override
    public ResponseEntity<?> resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> optionalUser = repository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found with email: " + email));
        }

        AuthUser user = optionalUser.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Current password is incorrect!"));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password reset successfully!"));
    }
}
