package com.borscheva.spring.rest.requestprocessor.exception;

import com.borscheva.spring.rest.requestprocessor.dto.NotificationApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<NotificationApiResponse> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .sorted()
                .toList();

        log.warn("Validation failed: {}", errors);

        return ResponseEntity.badRequest()
                .body(NotificationApiResponse.validationError("Validation failed", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<NotificationApiResponse> handleBadJson(
            HttpMessageNotReadableException ex
    ) {
        log.warn("Invalid request body: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(NotificationApiResponse.error(
                        "Invalid request format. Allowed types: SMS, EMAIL, PUSH, TG_MESSAGE"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<NotificationApiResponse> handleStrategyNotFound(
            IllegalArgumentException ex
    ) {
        log.warn("Strategy not found: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(NotificationApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<NotificationApiResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected error: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(NotificationApiResponse.error("Internal server error"));
    }
}