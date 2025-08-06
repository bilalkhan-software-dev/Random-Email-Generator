package com.randomEmailGenerator.controller;

import com.randomEmailGenerator.dto.AuthResponse;
import com.randomEmailGenerator.dto.EmailResponse;
import com.randomEmailGenerator.dto.GenerateEmailResponse;
import com.randomEmailGenerator.dto.UserResponse;
import com.randomEmailGenerator.handler.GenericResponseHandler;
import com.randomEmailGenerator.services.AuthService;
import com.randomEmailGenerator.services.GeneratingEmailService;
import com.randomEmailGenerator.services.UserService;
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
    private final UserService userService;
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> userDetailsById(@PathVariable Integer userId) {

        UserResponse userDetailById = userService.getUserDetailById(userId);
        if (ObjectUtils.isEmpty(userDetailById)) {
            return responseHandler.createErrorResponseMessage("User detail retrieve failed!", HttpStatus.BAD_REQUEST);
        }
        return responseHandler.createBuildResponse("User detail retrieved successfully!", userDetailById, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUser() {

        List<UserResponse> allUsers = userService.getAllUsers();
        if (CollectionUtils.isEmpty(allUsers)) {
            return responseHandler.createErrorResponseMessage("No user register yet!", HttpStatus.BAD_REQUEST);
        }
        return responseHandler.createBuildResponse("Total users: " + allUsers.size(), allUsers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return responseHandler.createBuildResponseMessage("User deleted successfully with id: " + userId, HttpStatus.OK);
    }

    @PutMapping("/disable/userId")
    public ResponseEntity<?> disableUser(@PathVariable Integer userId) {
        UserResponse response = userService.disableAndEnableUser(userId);
        String message = response.getIsEnabled() ? "User is enabled successfully!" : "User is disabled successfully!";
        return responseHandler.createBuildResponse(message, response, HttpStatus.OK);
    }

}
