package ru.atomskills.back.configs;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestAdviser {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(
                ErrorResponse.builder()
                        .exception(e.getClass().getSimpleName())
                        .message(e.getMessage())
                        .build()
        );
    }

    @Data
    @Builder
    private static class ErrorResponse {
        private String exception;
        private String message;
    }

}
