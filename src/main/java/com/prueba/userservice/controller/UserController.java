package com.prueba.userservice.controller;

import com.prueba.userservice.dto.ApiResponse;
import com.prueba.userservice.dto.ForgotPasswordRequest;
import com.prueba.userservice.dto.ResetPasswordRequest;
import com.prueba.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Gestión de registro y recuperación de usuarios")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario")
    public ApiResponse register(@RequestParam String name,
                                @RequestParam String email) {

        userService.register(name, email);
        return new ApiResponse("Usuario registrado correctamente");
    }

    @GetMapping("/activate")
    @Operation(summary = "Activar usuario con token")
    public ApiResponse activate(@RequestParam String token) {

        String message = userService.activateUser(token);
        return new ApiResponse(message);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar token de recuperación")
    public ApiResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {

        String token = userService.sendPasswordResetToken(request.getEmail());
        return new ApiResponse("Token generado: " + token);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Restablecer contraseña")
    public ApiResponse resetPassword(@RequestBody ResetPasswordRequest request) {

        userService.resetPassword(request.getToken(), request.getNewPassword());
        return new ApiResponse("Contraseña restablecida correctamente");
    }
}