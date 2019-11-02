package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class DefaultEmailServiceTest {
    private static final JavaMailSender mockSender = mock(JavaMailSender.class);
    private static final EmailConfigurationProperties mockProperties = mock(EmailConfigurationProperties.class);
    private static final MimeMessage mockMessage = mock(MimeMessage.class);
    private static final TemplateService mockTemplateService = mock(TemplateService.class);
    private static final String testString = "Test";
    private static MessageParameters parameters;
    private static MessageParameters.Builder builder;

    private EmailService service = new DefaultEmailService(mockSender, mockProperties, mockTemplateService);

    @BeforeAll
    static void setUp() {
        when(mockSender.createMimeMessage()).thenReturn(mockMessage);
        when(mockProperties.getUsername()).thenReturn("Anonimous");
        builder = new MessageParameters.Builder()
                .setToAddresses("some@ukr.net")
                .setCcAddresses("some@gmail.com")
                .setBccAddresses("another@ukr.net")
                .setSubject(testString)
                .setText(testString)
                .setHtml(testString);
        when(mockTemplateService.convert(any(MessageParameters.class), any(EmailContentType.class))).thenReturn(testString);
    }

    @Test
    public void shouldReturnTrueWhenSendMessageWithTemplate() {
        //given
        parameters = builder.setNameOfTemplate("some-template.html")
                .addTemplateParameter("userName", "Obama")
                .build();
        Mockito.doNothing().when(mockSender).send(any(MimeMessage.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertTrue(successful);
    }

    @Test
    public void shouldReturnTrueWhenSendMessageWithoutTemplate() {
        //given
        parameters = builder.setNameOfTemplate(null).build();
        Mockito.doNothing().when(mockSender).send(any(MimeMessage.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertTrue(successful);
    }

    @Test
    public void shouldThrowExceptionWhenSendMessage() {
        //given
        Mockito.doThrow(new MailSendException("")).when(mockSender).send(any(MimeMessage.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertFalse(successful);
    }
}
