package com.prueba.userservice.service;

import com.prueba.userservice.dto.LoginRequest;
import com.prueba.userservice.dto.LoginResponse;
import com.prueba.userservice.model.User;
import com.prueba.userservice.repository.UserRepository;
import com.prueba.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 🔴 VALIDAR ACTIVACIÓN
        if (!user.isActive()) {
            throw new RuntimeException("Usuario no activado");
        }

        // 🔴 VALIDAR PASSWORD
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}