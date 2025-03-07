package com.example.greetingsapp.service;

import com.example.greetingsapp.dto.AuthUserDTO;
import com.example.greetingsapp.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IAuthService {

    String registerUser(AuthUserDTO dto);

    String loginUser(LoginDTO dto);

    ResponseEntity<?> forgotPassword(String email, String newPassword);

    ResponseEntity<?> resetPassword(String email, String currentPassword, String newPassword);
}
