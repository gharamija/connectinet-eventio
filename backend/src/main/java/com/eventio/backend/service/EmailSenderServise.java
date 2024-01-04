package com.eventio.backend.service;

public interface EmailSenderServise {
    void sendSimpleEmail(String toEmail, String subject, String body);
}
