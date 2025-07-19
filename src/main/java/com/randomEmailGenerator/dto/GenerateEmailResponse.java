package com.randomEmailGenerator.dto;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GenerateEmailResponse {

    private String username;
    private String fullName;
    private Long totalEmailGenerated;

    @Builder.Default
    private List<Email> email = new ArrayList<>();



    @Data
    @Builder
    public static class Email {
        private Integer id;
        private String email;
    }

}
