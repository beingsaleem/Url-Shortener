package com.url.shortener.exceptions;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// This annotation makes it a global exception handler
@ControllerAdvice
public class GlobalExceptionHandler {

    // This annotation specifies that this method handles InvalidUrlException
    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Object> handleInvalidUrlException(InvalidUrlException exception) {

        // create a clear, structured error response body
        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
