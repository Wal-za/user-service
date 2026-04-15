package com.prueba.userservice.service;

import com.prueba.userservice.model.User;
import com.prueba.userservice.model.VerificationToken;
import com.prueba.userservice.repository.UserRepository;
import com.prueba.userservice.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // 👈 NUEVO

    // =========================
    // REGISTER
    // =========================
    public void register(String name, String email) {

        userRepository.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("El correo ya está registrado");
        });

        User user = User.builder()
                .fullName(name)
                .email(email)
                .active(false)
                .password(null)
                .build();

        userRepository.save(user);

        invalidatePreviousToken(user);

        String tokenValue = UUID.randomUUID().toString();

        VerificationToken token = VerificationToken.builder()
                .token(tokenValue)
                .expirationDate(LocalDateTime.now().plusHours(24))
                .used(false)
                .user(user)
                .build();

        tokenRepository.save(token);

        // ✅ REEMPLAZO SYSTEM.OUT
        String link = "http://localhost:8080/users/activate?token=" + tokenValue;

        emailService.sendEmail(
                email,
                "Activación de cuenta",
                "Hola, usa este enlace para activar tu cuenta:\n\n" + link
        );
    }

    // =========================
    // ACTIVATE USER
    // =========================
    public String activateUser(String tokenValue) {

        VerificationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        validateToken(token);

        User user = token.getUser();
        user.setActive(true);

        String rawPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(rawPassword));

        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);

        // ✅ REEMPLAZO SYSTEM.OUT
        emailService.sendEmail(
                user.getEmail(),
                "Cuenta activada",
                "Tu cuenta fue activada correctamente.\n\nTu contraseña es: " + rawPassword
        );

        return "Usuario activado correctamente";
    }

    // =========================
    // FORGOT PASSWORD
    // =========================
    public String sendPasswordResetToken(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Correo o usuario incorrecto"));

        if (!user.isActive()) {
            throw new RuntimeException("Usuario no activado. No puede recuperar contraseña");
        }

        invalidatePreviousToken(user);

        String tokenValue = UUID.randomUUID().toString();

        VerificationToken token = VerificationToken.builder()
                .token(tokenValue)
                .expirationDate(LocalDateTime.now().plusHours(1))
                .used(false)
                .user(user)
                .build();

        tokenRepository.save(token);

        // ✅ REEMPLAZO SYSTEM.OUT
        String link = "http://localhost:8080/users/reset-password?token=" + tokenValue;

        emailService.sendEmail(
                user.getEmail(),
                "Recuperación de contraseña",
                "Haz clic en el siguiente enlace para restablecer tu contraseña:\n\n" + link
        );

        return tokenValue;
    }

    // =========================
    // RESET PASSWORD
    // =========================
    public void resetPassword(String tokenValue, String newPassword) {

        VerificationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        validateToken(token);

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);

        // opcional: aviso por correo
        emailService.sendEmail(
                user.getEmail(),
                "Contraseña actualizada",
                "Tu contraseña fue actualizada correctamente."
        );
    }

    // =========================
    // FIND BY EMAIL
    // =========================
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // =========================
    // HELPERS
    // =========================
    private void validateToken(VerificationToken token) {

        if (token.isUsed()) {
            throw new RuntimeException("Token ya usado");
        }

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }
    }

    private void invalidatePreviousToken(User user) {

        tokenRepository.findFirstByUserAndUsedFalse(user)
                .ifPresent(existing -> {
                    existing.setUsed(true);
                    tokenRepository.save(existing);
                });
    }
}