package com.randomEmailGenerator.services;

import com.randomEmailGenerator.dto.EmailResponse;
import com.randomEmailGenerator.dto.GenerateEmailResponse;

import java.util.List;

public interface GeneratingEmailService {


    GenerateEmailResponse generateRandomPakistaniGirlsName(Integer length);
    GenerateEmailResponse generateRandomPakistaniBoysName(Integer length);
    GenerateEmailResponse generateRandomPakistaniGirlsAndBoysName(Integer length);

    GenerateEmailResponse generateRandomIndianGirlsName(Integer length);
    GenerateEmailResponse generateRandomIndianBoysName(Integer length);
    GenerateEmailResponse generateRandomIndianGirlsAndBoysName(Integer length);

    EmailResponse saveSelectedEmail(String email);

    GenerateEmailResponse getUserSavedEmail();

    GenerateEmailResponse generatedEmailNotSavedByUser(Integer length,String category);

    GenerateEmailResponse generateByCategory(String category, Integer length);

}
