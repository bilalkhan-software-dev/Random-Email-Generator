package com.randomEmailGenerator.services;

import com.randomEmailGenerator.dto.GenerateEmailResponse;

import java.util.List;

public interface GeneratingEmailService {


    GenerateEmailResponse generateRandomPakistaniGirlsName(Integer length);
    GenerateEmailResponse generateRandomPakistaniBoysName(Integer length);
    GenerateEmailResponse generateRandomPakistaniGirlsAndBoysName(Integer length);

    GenerateEmailResponse generateRandomIndianGirlsName(Integer length);
    GenerateEmailResponse generateRandomIndianBoysName(Integer length);
    GenerateEmailResponse generateRandomIndianGirlsAndBoysName(Integer length);

    boolean saveSelectedEmail(String email);

    List<GenerateEmailResponse> getUserSavedEmail();
}
