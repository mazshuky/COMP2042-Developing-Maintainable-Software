package com.example.demo.controller;

// Custom exception to handle multiple exceptions cleanly
public class CustomException extends Exception {
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
