package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.model.PasswordResetToken;
import com.example.hotelbookingapp.model.User;
import com.example.hotelbookingapp.repository.PasswordResetTokenRepository;
import com.example.hotelbookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    // Step 1: user requests a reset link
    @Transactional
    public void requestPasswordReset(String email) {
        // We always respond with success — never reveal whether the email exists
        userRepository.findByEmail(email).ifPresent(user -> {
            // Invalidate any previously issued tokens for this user
            tokenRepository.deleteByUser(user);

            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(30));

            tokenRepository.save(resetToken);

            sendResetEmail(user, token);
        });
    }

    // Step 2: user submits the new password
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset link."));

        if (resetToken.isUsed()) {
            throw new IllegalArgumentException("This reset link has already been used.");
        }

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("This reset link has expired. Please request a new one.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Mark the token as used so it cannot be reused
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    private void sendResetEmail(User user, String token) {
        String resetLink = frontendUrl + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Reset your password – Hotel Booking");
        message.setText(
                "Hi " + user.getName() + ",\n\n" +
                "We received a request to reset your password.\n\n" +
                "Click the link below to set a new password (valid for 30 minutes):\n\n" +
                resetLink + "\n\n" +
                "If you didn't request this, you can safely ignore this email — " +
                "your password will remain unchanged.\n\n" +
                "The Hotel Booking Team"
        );

        mailSender.send(message);
    }

}
