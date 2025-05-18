package com.example.E_Library_API.exception;

public class IsbnAlreadyIsTaken extends RuntimeException {
    public IsbnAlreadyIsTaken(String message) {
        super(message);
    }
}
