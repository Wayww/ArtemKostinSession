package com.kostinartem.courseplatform.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class KostinArtemGlobalExceptionHandler {

    @ExceptionHandler(KostinArtemNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(KostinArtemNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(KostinArtemBadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(KostinArtemBadRequestException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KostinArtemConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(KostinArtemConflictException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
        StringBuilder message = new StringBuilder("Validation failed");
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            message.append(": ")
                    .append(fieldError.getField())
                    .append(" ")
                    .append(fieldError.getDefaultMessage());
            break;
        }
        return buildResponse(message.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception exception) {
        return buildResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("status", status.value());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(body);
    }
}
