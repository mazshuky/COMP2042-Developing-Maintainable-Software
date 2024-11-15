package com.example.demo.controller;

// Create a custom exception to make code cleaner and easier to manage
// Able to handle multiple exceptions in a single catch block
// Reduces the need to import specific exceptions in main class
public class CustomException extends Exception {
    public CustomException(Throwable cause) {
        super(cause);
    }
}
