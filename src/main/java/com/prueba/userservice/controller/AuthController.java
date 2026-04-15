package com.prueba.userservice.controller;

import com.prueba.userservice.dto.LoginRequest;
import com.prueba.userservice.dto.LoginResponse;
import com.prueba.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Autenticación con JWT")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener JWT")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}