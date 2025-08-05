package com.randomEmailGenerator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmailResponse {

    private Integer id;
    private String email;
    private LocalDateTime createdAt;
    private Boolean isSelected;
    private CreatedUserDetails userDetails ;



    @Data
    @Builder
    public static class CreatedUserDetails{
        private Integer userId;
        private String username;
        private String fullName;
    }
}
