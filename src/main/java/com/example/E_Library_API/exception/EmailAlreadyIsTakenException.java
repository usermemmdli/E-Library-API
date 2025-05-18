package com.example.E_Library_API.exception;

public class EmailAlreadyIsTakenException extends RuntimeException {
    public EmailAlreadyIsTakenException(String message) {
        super(message);
    }
}
