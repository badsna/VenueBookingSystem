package com.example.userservice.externalservice;

import com.example.userservice.model.EmailSender;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL-SERVICE")
public interface EmailSenderService {
    @PostMapping("api/email/sendEmail")
     void sendEmail(@RequestBody EmailSender emailSender);
}
