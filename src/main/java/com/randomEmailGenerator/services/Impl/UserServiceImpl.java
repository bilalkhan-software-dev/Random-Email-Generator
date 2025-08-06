package com.randomEmailGenerator.services.Impl;

import com.randomEmailGenerator.dto.UserResponse;
import com.randomEmailGenerator.entity.User;
import com.randomEmailGenerator.exception.ResourceNotFoundException;
import com.randomEmailGenerator.repository.UserRepository;
import com.randomEmailGenerator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

        UserResponse response;

        if (user.getIsEnabled()) {
            response = UserResponse.builder()
                    .userId(user.getId())
                    .fullName(user.getFullName())
                    .username(user.getUsername())
                    .isEnabled(true)
                    .totalSavedEmails(user.getGeneratedEmail().size())
                    .build();
        }

        user.setIsEnabled(false);
        User isEnabled = userRepository.save(user);
        response = UserResponse.builder()
                .userId(isEnabled.getId())
                .fullName(isEnabled.getFullName())
                .username(isEnabled.getUsername())
                .isEnabled(false)
                .totalSavedEmails(isEnabled.getGeneratedEmail().size())
                .build();

        return response;
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
