package com.example.bookingservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSender {
    private String toEmail;
    private String body;
    private String subject;
}
