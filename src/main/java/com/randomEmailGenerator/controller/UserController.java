package com.randomEmailGenerator.controller;

import com.randomEmailGenerator.dto.AuthResponse;
import com.randomEmailGenerator.dto.EmailResponse;
import com.randomEmailGenerator.dto.GenerateEmailResponse;
import com.randomEmailGenerator.handler.GenericResponseHandler;
import com.randomEmailGenerator.services.AuthService;
import com.randomEmailGenerator.services.GeneratingEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final GeneratingEmailService generatingEmailService;
    private final AuthService authService;
    private final GenericResponseHandler responseHandler;

    @PostMapping("/save-email/{email}")
    public ResponseEntity<?> saveSelectedEmail(@PathVariable String email) {

        EmailResponse emailResponse = generatingEmailService.saveSelectedEmail(email);

        if (emailResponse != null) {
            return responseHandler.createBuildResponse("Email saved successfully!", emailResponse, HttpStatus.CREATED);
        }
        return responseHandler.createErrorResponseMessage("Email not saved!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/saved-emails/")
    public ResponseEntity<?> getUserSavedEmail() {

        GenerateEmailResponse userSavedEmail = generatingEmailService.getUserSavedEmail();

        if (!ObjectUtils.isEmpty(userSavedEmail)) {
            return responseHandler.createBuildResponse("User saved email retrieved successfully!", userSavedEmail, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter-emails/{length}")
    public ResponseEntity<?> generatedEmailNotSavedByUser(@PathVariable Integer length, @RequestParam String category) {

        GenerateEmailResponse generateEmailResponse = generatingEmailService.generatedEmailNotSavedByUser(length, category);

        if (generateEmailResponse != null) {
            return responseHandler.createBuildResponse("Email generated successfully!", generateEmailResponse, HttpStatus.OK);
        }
        return responseHandler.createErrorResponseMessage("Email not generated successfully!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> fetchUserProfile() {
        AuthResponse profileResponse = authService.userProfile();
        if (profileResponse != null) {
            return responseHandler.createBuildResponse("User profile retrieved successfully!", profileResponse, HttpStatus.OK);
        }
        return responseHandler.createErrorResponseMessage("User not retrieved successfully!", HttpStatus.BAD_REQUEST);
    }
}
