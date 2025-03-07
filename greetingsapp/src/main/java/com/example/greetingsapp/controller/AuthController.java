package com.example.greetingsapp.controller;

import com.example.greetingsapp.dto.AuthUserDTO;
import com.example.greetingsapp.dto.LoginDTO;
import com.example.greetingsapp.service.AuthService;
import com.example.greetingsapp.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;    // Inject Interface, not class

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AuthUserDTO dto) {
        String response = authService.registerUser(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO dto) {
        String token = authService.loginUser(dto);
        return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
    }

    @PutMapping("/forgot/{email}")
    public ResponseEntity<?> forgotPassword(
            @PathVariable String email,
            @RequestBody Map<String, String> requestBody) {

        String newPassword = requestBody.get("password");
        return authService.forgotPassword(email, newPassword);
    }

    @PutMapping("/reset/{email}")
    public ResponseEntity<?> resetPassword(
            @PathVariable String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {

        return authService.resetPassword(email, currentPassword, newPassword);
    }
}
