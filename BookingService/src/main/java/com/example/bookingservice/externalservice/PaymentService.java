package com.example.bookingservice.externalservice;

import com.example.bookingservice.dto.PaymentRequestDto;
import com.example.bookingservice.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "external", url = "https://8842a55f-9a25-46fb-bf35-9df95ade13d2.mock.pstmn.io")
public interface PaymentService {
    @PostMapping("/makePayment/eSewa")
    PaymentResponseDto makePaymentUsingeSewa(@RequestBody PaymentRequestDto paymentRequestDto);

    @PostMapping("/makePayment/fonePay")
    PaymentResponseDto makePaymentUsingfonePay(@RequestBody PaymentRequestDto paymentRequestDto);

    @PostMapping("/makePayment/ImePay")
    PaymentResponseDto makePaymentUsingImePay(@RequestBody PaymentRequestDto paymentRequestDto);
}
