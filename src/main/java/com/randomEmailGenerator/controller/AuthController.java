package com.randomEmailGenerator.controller;


import com.randomEmailGenerator.dto.LoginRequest;
import com.randomEmailGenerator.dto.RegisterRequest;
import com.randomEmailGenerator.dto.AuthResponse;
import com.randomEmailGenerator.handler.GenericResponseHandler;
import com.randomEmailGenerator.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final GenericResponseHandler responseHandler;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest authRequest) {

        AuthResponse isRegistered = authService.registerUser(authRequest);

        if (isRegistered != null) {
            return responseHandler.createBuildResponse("Account registered Successfully!", isRegistered, HttpStatus.CREATED);
        }
        return responseHandler.createErrorResponseMessage("Account creation failed!. Try again later", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.loginUser(loginRequest);
        if (authResponse != null) {
            return responseHandler.createBuildResponse("Login successful", authResponse, HttpStatus.OK);
        }
        return responseHandler.createErrorResponseMessage("Authentication failed!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
