package com.prueba.userservice.service;

import com.prueba.userservice.model.User;

public interface IUserService {

    void register(String name, String email);

    String activateUser(String token);

    String sendPasswordResetToken(String email);

    void resetPassword(String token, String newPassword);

    User findByEmail(String email);
}