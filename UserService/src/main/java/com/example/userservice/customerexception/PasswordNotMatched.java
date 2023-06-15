package com.example.userservice.customerexception;

public class PasswordNotMatched extends RuntimeException{
    public PasswordNotMatched(String message) {
        super(message);
    }
}
