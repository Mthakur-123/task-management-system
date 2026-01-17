package com.example2.taskmanager.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import com.example2.taskmanager.DTO.ApiResponse;
import com.example2.taskmanager.DTO.AuthResponse;
import com.example2.taskmanager.DTO.LoginRequest;
import com.example2.taskmanager.DTO.RegisterRequest;
import com.example2.taskmanager.Model.User;
import com.example2.taskmanager.Repository.UserRepository;
import com.example2.taskmanager.Security.JwtUtil;
import com.example2.taskmanager.Service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
      
      
    @Autowired
    private  UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @RequestBody RegisterRequest request
    ) {
          userService.register(request);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "User registered successfully",
                LocalDateTime.now(),
                request.getUsername()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody LoginRequest request
    ) {
        AuthResponse authResponse = userService.login(request);

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Login successful",
                LocalDateTime.now(),
                authResponse
        );

        return ResponseEntity
        		.ok(response);
    }
    
    
}
