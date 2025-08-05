package com.randomEmailGenerator.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse {

    private HttpStatus httpStatus;
    private String message;
    private String status;
    private Object data;

    public ResponseEntity<?> createResponse() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

}
