package com.randomEmailGenerator.handler;

import com.randomEmailGenerator.exception.EmailAlreadyExistException;
import com.randomEmailGenerator.exception.JwtTokenExpiredException;
import com.randomEmailGenerator.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final GenericResponseHandler responseHandler;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception exception) {
        return responseHandler.createErrorResponseMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> usernameAlreadyTakenExceptionHandler(EmailAlreadyExistException exception) {
        return responseHandler.createErrorResponseMessage(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        return responseHandler.createErrorResponseMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return responseHandler.createErrorResponseMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundExceptionHandler(UsernameNotFoundException exception) {
        return responseHandler.createErrorResponseMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException exception) {
        return responseHandler.createErrorResponseMessage("Password is wrong!", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<?> jwtExceptionHandler(JwtTokenExpiredException exception) {
        return responseHandler.createErrorResponseMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException e) {
        return responseHandler.createErrorResponseMessage("You do not have permission to perform this action.", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return responseHandler.createErrorResponseMessage("Malformed JSON request", HttpStatus.BAD_REQUEST);
    }


}
