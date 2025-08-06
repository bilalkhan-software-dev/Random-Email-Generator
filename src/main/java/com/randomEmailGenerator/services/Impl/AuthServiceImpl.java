package com.randomEmailGenerator.services.Impl;

import com.randomEmailGenerator.config.security.CustomUserDetails;
import com.randomEmailGenerator.dto.RegisterRequest;
import com.randomEmailGenerator.dto.AuthResponse;
import com.randomEmailGenerator.dto.LoginRequest;
import com.randomEmailGenerator.entity.User;
import com.randomEmailGenerator.exception.EmailAlreadyExistException;
import com.randomEmailGenerator.repository.UserRepository;
import com.randomEmailGenerator.services.AuthService;
import com.randomEmailGenerator.services.JwtService;
import com.randomEmailGenerator.util.GetLoggedInUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final GetLoggedInUserDetails loggedInUserDetails;

    @Override
    public AuthResponse registerUser(RegisterRequest authRequest) {

        boolean isAlreadyRegistered = userRepository.existsByUsername(authRequest.getUsername());

        if (isAlreadyRegistered) {
            log.error("Username is already in use");
            throw new EmailAlreadyExistException("Username already taken!");
        }


        User user = User.builder()
                .username(authRequest.getUsername())
                .fullName(authRequest.getFullName())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .isEnabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        User isSaved = userRepository.save(user);
        String token = jwtService.generateToken(isSaved);

        return AuthResponse.builder()
                .id(isSaved.getId())
                .createdAt(isSaved.getCreatedAt())
                .fullName(isSaved.getFullName())
                .username(isSaved.getUsername())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No account is registered with this username!")
        );
        if (!user.getIsEnabled()) {
            throw new DisabledException("Your account has been disabled");
        }


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Authentication failed");
        }

        if (authentication.isAuthenticated()) {
            log.info("User is authenticate with email: {} and  now generating token", username);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails.getUser());

            // Successful login
            return AuthResponse.builder()
                    .id(userDetails.getUser().getId())
                    .username(userDetails.getUser().getUsername())
                    .fullName(userDetails.getUser().getFullName())
                    .createdAt(userDetails.getUser().getCreatedAt())
                    .token(token)
                    .build();
        }
        return null;
    }

    @Override
    public AuthResponse userProfile() {

        User user = loggedInUserDetails.getAuthenticatedUser();


        return AuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .token("In Profile no token is generated")
                .createdAt(user.getCreatedAt())
                .build();
    }
}
