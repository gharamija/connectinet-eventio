package com.eventio.backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

class EmailSenderServiceJpaTest {

  @Mock
  private JavaMailSender mailSender;

  @InjectMocks
  private EmailSenderServiceJpa emailSenderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSendSimpleEmail() {
    String toEmail = "test@example.com";
    String subject = "Test Subject";
    String body = "Test Body";

    emailSenderService.sendSimpleEmail(toEmail, subject, body);

    verify(mailSender).send(createExpectedSimpleMailMessage(toEmail, subject, body));
  }

  private SimpleMailMessage createExpectedSimpleMailMessage(String toEmail, String subject, String body) {
    SimpleMailMessage expectedMessage = new SimpleMailMessage();
    expectedMessage.setFrom("eventio.progi@gmail.com");
    expectedMessage.setTo(toEmail);
    expectedMessage.setSubject(subject);
    expectedMessage.setText(body);
    return expectedMessage;
  }
}
