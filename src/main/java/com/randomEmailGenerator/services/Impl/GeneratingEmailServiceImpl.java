package com.randomEmailGenerator.services.Impl;

import com.randomEmailGenerator.dto.GenerateEmailResponse;
import com.randomEmailGenerator.repository.GeneratedEmailRepository;
import com.randomEmailGenerator.services.GeneratingEmailService;
import static com.randomEmailGenerator.util.Names.PAKISTANI_BOYS_NAMES;
import static com.randomEmailGenerator.util.Names.PAKISTANI_GIRLS_NAMES;
import static com.randomEmailGenerator.util.Names.INDIAN_BOYS_NAMES;
import static com.randomEmailGenerator.util.Names.INDIAN_GIRLS_NAMES;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GeneratingEmailServiceImpl implements GeneratingEmailService {

    private final GeneratedEmailRepository generatedEmailRepository;

    private final Random random = new Random();

    @Override
    public GenerateEmailResponse generateRandomPakistaniGirlsName(Integer length) {

        List<String> pakistaniBoysNames = PAKISTANI_BOYS_NAMES;


        return null;
    }

    @Override
    public GenerateEmailResponse generateRandomPakistaniBoysName(Integer length) {

        List<String> pakistaniGirlsNames = PAKISTANI_GIRLS_NAMES;
        return null;
    }

    @Override
    public GenerateEmailResponse generateRandomPakistaniGirlsAndBoysName(Integer length) {

        List<String> pakistaniBoysNames = PAKISTANI_BOYS_NAMES;
        List<String> pakistaniGirlsNames = PAKISTANI_GIRLS_NAMES;


        return null;
    }

    @Override
    public GenerateEmailResponse generateRandomIndianBoysName(Integer length) {

        List<String> indianBoysNames = INDIAN_BOYS_NAMES;

        return null;
    }

    @Override
    public GenerateEmailResponse generateRandomIndianGirlsName(Integer length) {

        List<String> indianGirlsNames = INDIAN_GIRLS_NAMES;

        return null;
    }

    @Override
    public GenerateEmailResponse generateRandomIndianGirlsAndBoysName(Integer length) {

        List<String> indianBoysNames = INDIAN_BOYS_NAMES;
        List<String> indianGirlsNames = INDIAN_GIRLS_NAMES;

        return null;
    }

    @Override
    public boolean saveSelectedEmail(String email) {

        return false;
    }

    @Override
    public List<GenerateEmailResponse> getUserSavedEmail() {
        return List.of();
    }
}
