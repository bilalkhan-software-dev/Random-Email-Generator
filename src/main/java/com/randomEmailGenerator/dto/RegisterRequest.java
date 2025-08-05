package com.randomEmailGenerator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    private String fullName;
    private String username;
    private String password;
}
