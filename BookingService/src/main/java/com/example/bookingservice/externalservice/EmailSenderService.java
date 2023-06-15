package com.example.bookingservice.externalservice;

import com.example.bookingservice.model.EmailSender;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL-SERVICE")
public interface EmailSenderService {
    @PostMapping("api/email/sendEmail")
     void sendEmail(@RequestBody EmailSender emailSender);
}
