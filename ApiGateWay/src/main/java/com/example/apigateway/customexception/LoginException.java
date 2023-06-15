package com.example.apigateway.customexception;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}
