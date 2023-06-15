package com.example.bookingservice.customexception;

public class VenueNotAvailableException extends RuntimeException{
    public VenueNotAvailableException(String message) {
        super(message);
    }
}
