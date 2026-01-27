package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.RegistrationRequest;
import com.example.hotelbookingapp.enums.Role;
import com.example.hotelbookingapp.model.User;
import com.example.hotelbookingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/api/auth")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists.");
        }

        User user = User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();

        userRepository.save(user);

    }

}