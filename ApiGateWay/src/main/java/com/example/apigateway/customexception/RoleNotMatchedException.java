package com.example.apigateway.customexception;

public class RoleNotMatchedException extends RuntimeException{
    public RoleNotMatchedException(String message) {
        super(message);
    }
}
