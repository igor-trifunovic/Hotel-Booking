package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.AuthResponse;
import com.example.hotelbookingapp.dto.LoginRequest;
import com.example.hotelbookingapp.dto.RegistrationRequest;
import com.example.hotelbookingapp.enums.Role;
import com.example.hotelbookingapp.model.User;
import com.example.hotelbookingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        if (!passwordEncoder.matches(
                request.password(), user.getPassword())) {
            throw new RuntimeException("Credentials don't match.");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);

    }

    public void register(RegistrationRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already exists.");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

    }

}