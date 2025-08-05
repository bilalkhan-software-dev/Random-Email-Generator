package com.randomEmailGenerator.util;

import com.randomEmailGenerator.config.security.CustomUserDetails;
import com.randomEmailGenerator.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetLoggedInUserDetails {

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }

        return null;
    }

    public Optional<User> getAuthenticatedUserOptional() {
        return Optional.ofNullable(getAuthenticatedUser());
    }
}