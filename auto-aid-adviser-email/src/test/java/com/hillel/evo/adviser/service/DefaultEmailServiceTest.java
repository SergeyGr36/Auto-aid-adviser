package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.parameters.MessageParameters;
import org.junit.jupiter.api.Test;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

public class DefaultEmailServiceTest {
    private static final JavaMailSender mockSender = mock(JavaMailSender.class);
    private static final EmailConfigurationProperties mockProperties = mock(EmailConfigurationProperties.class);
    private static final MimeMessage mockMessage = mock(MimeMessage.class);

    private EmailService service = new DefaultEmailService(mockSender, mockProperties);

    @Test
    public void sendMessage() {
        //given
        when(mockSender.createMimeMessage()).thenReturn(mockMessage);
        when(mockProperties.getUsername()).thenReturn("Anonimous");
        MessageParameters parameters = new MessageParameters.Builder()
                .setToAddresses("some@ukr.net")
                .setCcAddresses("some@gmail.com")
                .setBccAddresses("another@ukr.net")
                .setSubject("Test")
                .setText("Test")
                .setHtml("<H1>Test</H1>")
                .build();
        boolean successful = service.sendMessage(parameters);
        assertTrue(successful);
    }
}
