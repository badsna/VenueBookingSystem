package com.example.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private String payment_option;
    private double amount;
}
