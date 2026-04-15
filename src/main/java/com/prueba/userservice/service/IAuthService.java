package com.prueba.userservice.service;

import com.prueba.userservice.dto.LoginRequest;
import com.prueba.userservice.dto.LoginResponse;

public interface IAuthService {

    LoginResponse login(LoginRequest request);
}
