package com.example.bookingservice.customexception;

public class DateMisMatchedException extends RuntimeException{
    public DateMisMatchedException(String message) {
        super(message);
    }
}
