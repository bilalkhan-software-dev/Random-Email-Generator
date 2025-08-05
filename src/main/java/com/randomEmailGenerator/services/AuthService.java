package com.randomEmailGenerator.services;

import com.randomEmailGenerator.dto.RegisterRequest;
import com.randomEmailGenerator.dto.AuthResponse;
import com.randomEmailGenerator.dto.LoginRequest;

public interface AuthService {

    AuthResponse registerUser(RegisterRequest authRequest);

    AuthResponse loginUser(LoginRequest loginRequest);


    AuthResponse userProfile();
}
