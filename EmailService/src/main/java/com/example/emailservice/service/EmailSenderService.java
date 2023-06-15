package com.example.emailservice.service;

import com.example.emailservice.model.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailSender emailSender) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("badsnastha@gmail.com");
        mimeMessageHelper.setTo(emailSender.getToEmail());
        mimeMessageHelper.setSubject(emailSender.getSubject());
        mimeMessageHelper.setText(emailSender.getBody());

        mailSender.send(mimeMessage);
        System.out.println("You have logged in");
    }

}
