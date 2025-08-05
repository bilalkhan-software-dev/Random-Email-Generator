package com.randomEmailGenerator.services.Impl;

import com.randomEmailGenerator.dto.EmailResponse;
import com.randomEmailGenerator.dto.GenerateEmailResponse;
import com.randomEmailGenerator.entity.GeneratedEmail;
import com.randomEmailGenerator.entity.User;
import com.randomEmailGenerator.repository.GeneratedEmailRepository;
import com.randomEmailGenerator.services.GeneratingEmailService;

import static com.randomEmailGenerator.util.Names.PAKISTANI_BOYS_NAMES;
import static com.randomEmailGenerator.util.Names.PAKISTANI_GIRLS_NAMES;
import static com.randomEmailGenerator.util.Names.INDIAN_BOYS_NAMES;
import static com.randomEmailGenerator.util.Names.INDIAN_GIRLS_NAMES;
import static com.randomEmailGenerator.util.Constants.GMAIL_DOMAIN;

import com.randomEmailGenerator.util.GetLoggedInUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GeneratingEmailServiceImpl implements GeneratingEmailService {

    private final GeneratedEmailRepository generatedEmailRepository;
    private final GetLoggedInUserDetails getLoggedInUserDetails;
    private final Random random = new Random();

    @Override
    public GenerateEmailResponse generateRandomPakistaniGirlsName(Integer length) {
        List<String> names = PAKISTANI_GIRLS_NAMES;
        return generateEmailResponse(names, length, "pakistani_girls");
    }

    @Override
    public GenerateEmailResponse generateRandomPakistaniBoysName(Integer length) {
        List<String> names = PAKISTANI_BOYS_NAMES;
        return generateEmailResponse(names, length, "pakistani_boys");
    }

    @Override
    public GenerateEmailResponse generateRandomPakistaniGirlsAndBoysName(Integer length) {
        List<String> combinedNames = new ArrayList<>(PAKISTANI_BOYS_NAMES);
        combinedNames.addAll(PAKISTANI_GIRLS_NAMES);
        return generateEmailResponse(combinedNames, length, "pakistani_boys_and_girls");
    }

    @Override
    public GenerateEmailResponse generateRandomIndianBoysName(Integer length) {
        List<String> names = INDIAN_BOYS_NAMES;
        return generateEmailResponse(names, length, "indian_boys");
    }

    @Override
    public GenerateEmailResponse generateRandomIndianGirlsName(Integer length) {
        List<String> names = INDIAN_GIRLS_NAMES;
        return generateEmailResponse(names, length, "indian_girls");
    }

    @Override
    public GenerateEmailResponse generateRandomIndianGirlsAndBoysName(Integer length) {
        List<String> combinedNames = new ArrayList<>(INDIAN_BOYS_NAMES);
        combinedNames.addAll(INDIAN_GIRLS_NAMES);
        return generateEmailResponse(combinedNames, length, "indian_boys_and_girls");
    }

    @Override
    public GenerateEmailResponse generateByCategory(String category, Integer length) {

        return helperGenerateByCategoryAndLength(category, length);
    }


    private GenerateEmailResponse helperGenerateByCategoryAndLength(String category, Integer length) {
        GenerateEmailResponse response;
        response = switch (category) {
            case "pakistani_boys" -> generateRandomPakistaniBoysName(length);
            case "pakistani_girls" -> generateRandomPakistaniGirlsName(length);
            case "pakistani_boys_and_girls" -> generateRandomPakistaniGirlsAndBoysName(length);
            case "indian_boys" -> generateRandomIndianBoysName(length);
            case "indian_girls" -> generateRandomIndianGirlsName(length);
            case "indian_boys_and_girls" -> generateRandomIndianGirlsAndBoysName(length);
            default -> throw new IllegalArgumentException("Invalid category: " + category);
        };
        return response;
    }

    @Override
    public EmailResponse saveSelectedEmail(String email) {
        User authenticated = getLoggedInUserDetails.getAuthenticatedUser();

        GeneratedEmail generatedEmail = GeneratedEmail.builder()
                .email(email)
                .createdAt(LocalDateTime.now())
                .isSelected(true)
                .user(authenticated)
                .build();
        GeneratedEmail isEmailSaved = generatedEmailRepository.save(generatedEmail);

        return EmailResponse.builder()
                .email(isEmailSaved.getEmail())
                .createdAt(isEmailSaved.getCreatedAt())
                .isSelected(isEmailSaved.isSelected())
                .id(isEmailSaved.getId())
                .userDetails(EmailResponse.CreatedUserDetails.builder()
                        .userId(isEmailSaved.getUser().getId())
                        .fullName(isEmailSaved.getUser().getFullName())
                        .username(isEmailSaved.getUser().getUsername())
                        .build())
                .build();
    }

    @Override
    public GenerateEmailResponse generatedEmailNotSavedByUser(Integer length, String category) {
        User user = getLoggedInUserDetails.getAuthenticatedUser();

        // Get all emails saved by this user
        List<String> userSavedEmails = generatedEmailRepository.findByUser(user)
                .stream()
                .map(GeneratedEmail::getEmail)
                .toList();

        GenerateEmailResponse response;
        int attempts = 0;
        int maxAttempts = 10; // Prevent infinite loops

        do {

            response = helperGenerateByCategoryAndLength(category, length);

            // Checking if any generated emails match saved emails
            boolean hasMatch = response.getEmail().stream()
                    .anyMatch(generatedEmail ->
                            userSavedEmails.contains(generatedEmail.getEmail()));

            attempts++;


            // If no matches or max attempts reached, return the response
            if (!hasMatch || attempts >= maxAttempts) {
                response.setUsername(user.getUsername());
                response.setUsername(user.getUsername());
                return response;
            }

        } while (true);
    }

    @Override
    public GenerateEmailResponse getUserSavedEmail() {
        User user = getLoggedInUserDetails.getAuthenticatedUser();
        List<GeneratedEmail> savedEmails = generatedEmailRepository.findByUser(user);

        return GenerateEmailResponse.builder()
                        .username(user.getUsername())
                        .fullName(user.getFullName())
                        .totalEmailGenerated((long) savedEmails.size())
                        .email(savedEmails.stream().map(generatedEmail ->
                                GenerateEmailResponse.Email.builder()
                                        .id(generatedEmail.getId())
                                        .email(generatedEmail.getEmail())
                                        .createdAt(generatedEmail.getCreatedAt())
                                        .build()
                        ).toList())
                        .build();
    }

    private GenerateEmailResponse generateEmailResponse(List<String> names, int length, String usernamePrefix) {

        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("Names list cannot be empty");
        }

        List<GenerateEmailResponse.Email> emails = IntStream.range(0, length)
                .mapToObj(i -> {
                    String name = names.get(random.nextInt(names.size()));
                    String secondName = names.get(random.nextInt(names.size()));
                    String email = name + secondName + GMAIL_DOMAIN;

                    return GenerateEmailResponse.Email.builder()
                            .id(i + 1)
                            .email(email)
                            .createdAt(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());


        return GenerateEmailResponse.builder()
                .fullName(usernamePrefix)
                .username(usernamePrefix)
                .totalEmailGenerated((long) length)
                .email(emails)
                .build();
    }
}