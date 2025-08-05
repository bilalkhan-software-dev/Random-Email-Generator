package com.randomEmailGenerator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthResponse {

    private Integer id;
    private String fullName;
    private String username;
    private LocalDateTime createdAt;
    private String token;

}
