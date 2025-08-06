package com.randomEmailGenerator.services.Impl;

import com.randomEmailGenerator.dto.UserResponse;
import com.randomEmailGenerator.entity.GeneratedEmail;
import com.randomEmailGenerator.entity.User;
import com.randomEmailGenerator.exception.ResourceNotFoundException;
import com.randomEmailGenerator.repository.GeneratedEmailRepository;
import com.randomEmailGenerator.repository.UserRepository;
import com.randomEmailGenerator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GeneratedEmailRepository generatedEmailRepository;

    @Override
    public void deleteUser(Integer userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        userRepository.delete(user);
    }


    @Override
    public UserResponse disableAndEnableUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );

        if (!user.getUsername().equalsIgnoreCase("bilalkhan.devse@gmail.com")) {
            throw new IllegalArgumentException("Only father can enable/disable user");
        }


        // Toggle the enabled status
        user.setIsEnabled(!user.getIsEnabled());
        User updatedUser = userRepository.save(user);

        return UserResponse.builder()
                .userId(updatedUser.getId())
                .fullName(updatedUser.getFullName())
                .username(updatedUser.getUsername())
                .isEnabled(updatedUser.getIsEnabled())
                .totalSavedEmails(updatedUser.getGeneratedEmail().size())
                .build();
    }

    @Override
    public UserResponse getUserDetailById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        return UserResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .isEnabled(user.getIsEnabled())
                .totalSavedEmails(user.getGeneratedEmail().size())
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream().map(user -> UserResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .isEnabled(user.getIsEnabled())
                .totalSavedEmails(user.getGeneratedEmail().size())
                .build()).toList();
    }
}
