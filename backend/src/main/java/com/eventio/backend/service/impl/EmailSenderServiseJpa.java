package com.eventio.backend.service.impl;

import com.eventio.backend.service.EmailSenderServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiseJpa implements EmailSenderServise {
    @Autowired
    private JavaMailSender mailSender;


     @Override
     public void sendSimpleEmail(String toEmail, String subject, String body){
         SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom("eventio.progi@gmail.com");
         message.setTo(toEmail);
         message.setText(body);
         message.setSubject(subject);
         mailSender.send(message);
     }
}
