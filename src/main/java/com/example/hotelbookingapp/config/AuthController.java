package com.example.hotelbookingapp.config;

import com.example.hotelbookingapp.dto.AuthResponse;
import com.example.hotelbookingapp.dto.LoginRequest;
import com.example.hotelbookingapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

}