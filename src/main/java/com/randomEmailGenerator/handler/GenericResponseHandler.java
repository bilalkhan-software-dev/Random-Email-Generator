package com.randomEmailGenerator.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GenericResponseHandler {

    public ResponseEntity<?> createBuildResponse(String message, Object data, HttpStatus httpStatus) {
        GenericResponse response = GenericResponse.builder()
                .message(message)
                .data(data)
                .status("success")
                .httpStatus(httpStatus)
                .build();
        return response.createResponse();
    }

    public ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus httpStatus) {
        GenericResponse response = GenericResponse.builder()
                .message(message)
                .status("success")
                .httpStatus(httpStatus)
                .build();
        return response.createResponse();
    }

    public ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus httpStatus) {
        GenericResponse response = GenericResponse.builder()
                .message(message)
                .status("failed!")
                .httpStatus(httpStatus)
                .build();
        return response.createResponse();
    }


}
