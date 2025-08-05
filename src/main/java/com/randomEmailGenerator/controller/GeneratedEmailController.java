package com.randomEmailGenerator.controller;

import com.randomEmailGenerator.dto.GenerateEmailResponse;
import com.randomEmailGenerator.services.GeneratingEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class GeneratedEmailController {

    private final GeneratingEmailService generatingEmailService;

    @GetMapping("/pakistani/boys/{length}")
    public ResponseEntity<GenerateEmailResponse> generatePakistaniBoysEmails(
            @PathVariable Integer length) {
        return ResponseEntity.ok(
                generatingEmailService.generateRandomPakistaniBoysName(length)
        );
    }

    @GetMapping("/pakistani/girls/{length}")
    public ResponseEntity<GenerateEmailResponse> generatePakistaniGirlsEmails(
            @PathVariable Integer length) {
        return ResponseEntity.ok(
                generatingEmailService.generateRandomPakistaniGirlsName(length)
        );
    }

    @GetMapping("/pakistani/all/{length}")
    public ResponseEntity<GenerateEmailResponse> generatePakistaniEmails(
            @PathVariable Integer length) {
        return ResponseEntity.ok(
                generatingEmailService.generateRandomPakistaniGirlsAndBoysName(length)
        );
    }

    @GetMapping("/indian/boys/{length}")
    public ResponseEntity<GenerateEmailResponse> generateIndianBoysEmails(
            @PathVariable Integer length) {
        return ResponseEntity.ok(
                generatingEmailService.generateRandomIndianBoysName(length)
        );
    }

    @GetMapping("/indian/girls/{length}")
    public ResponseEntity<GenerateEmailResponse> generateIndianGirlsEmails(
            @PathVariable Integer length) {
        return ResponseEntity.ok(
                generatingEmailService.generateRandomIndianGirlsName(length)
        );
    }

    @GetMapping("/indian/all/{length}")
    public ResponseEntity<GenerateEmailResponse> generateIndianEmails(
            @PathVariable Integer length) {
        return ResponseEntity.ok(
                generatingEmailService.generateRandomIndianGirlsAndBoysName(length)
        );
    }

    @GetMapping("/{category}/{length}")
    public ResponseEntity<GenerateEmailResponse> generateEmailByCategory(@PathVariable String category, @PathVariable Integer length) {

        return ResponseEntity.ok(
                generatingEmailService.generateByCategory(category, length)
        );
    }
}