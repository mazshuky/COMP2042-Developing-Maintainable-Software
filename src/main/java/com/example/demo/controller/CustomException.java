package com.example.demo.controller;

/**
 * The {@code CustomException} class is a custom exception that handles multiple exceptions cleanly.
 * It extends the {@code Exception} class and provides constructors to create an exception with a message,
 * a cause, or both.
 */
public class CustomException extends Exception {
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
