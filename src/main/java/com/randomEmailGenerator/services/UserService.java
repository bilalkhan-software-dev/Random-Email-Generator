package com.randomEmailGenerator.services;

import com.randomEmailGenerator.dto.UserResponse;

import java.util.List;

public interface UserService {

    void deleteUser(Integer userId);
    UserResponse disableAndEnableUser(Integer userId);
    UserResponse getUserDetailById(Integer userId);
    List<UserResponse> getAllUsers();


}
