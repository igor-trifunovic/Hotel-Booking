package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.AuthResponse;
import com.example.hotelbookingapp.dto.LoginRequest;
import com.example.hotelbookingapp.dto.RegistrationRequest;
import com.example.hotelbookingapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegistrationRequest request) {
        authService.register(request);
    }

}