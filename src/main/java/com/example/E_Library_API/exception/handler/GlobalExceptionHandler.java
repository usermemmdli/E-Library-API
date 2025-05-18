package com.example.E_Library_API.exception.handler;

import com.example.E_Library_API.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            EmailAlreadyIsTakenException.class, HttpStatus.BAD_REQUEST,
            IsbnAlreadyIsTaken.class, HttpStatus.BAD_REQUEST,
            InvalidEmailOrPasswordException.class, HttpStatus.BAD_REQUEST,
            InvalidPasswordException.class, HttpStatus.BAD_REQUEST,
            InvalidValueException.class, HttpStatus.BAD_REQUEST
    );

    @ExceptionHandler({
            EntityNotFoundException.class,
            EmailAlreadyIsTakenException.class,
            IsbnAlreadyIsTaken.class,
            InvalidEmailOrPasswordException.class,
            InvalidPasswordException.class,
            InvalidValueException.class
    })
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(Exception e) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, status);
    }
}
