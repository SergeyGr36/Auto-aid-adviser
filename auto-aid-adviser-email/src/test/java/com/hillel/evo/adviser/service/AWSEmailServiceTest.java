package com.hillel.evo.adviser.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.MailSendException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AWSEmailServiceTest {
    private static final EmailConfigurationProperties mockProperties = mock(EmailConfigurationProperties.class);
    private static final TemplateService mockTemplateService = mock(TemplateService.class);
    private static final AmazonSimpleEmailService mockClient = mock(AmazonSimpleEmailService.class);
    private static final String testString = "Test";
    private static MessageParameters parameters;
    private static MessageParameters.Builder builder;

    private final EmailService service = new AWSEmailService(mockClient, mockProperties, mockTemplateService);

    @BeforeAll
    static void setUp() {
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
        when(mockClient.sendEmail(any(SendEmailRequest.class))).thenReturn(null);

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertTrue(successful);
    }

    @Test
    public void shouldReturnTrueWhenSendMessageWithoutTemplate() {
        //given
        parameters = builder.setNameOfTemplate(null).build();
        when(mockClient.sendEmail(any(SendEmailRequest.class))).thenReturn(null);

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertTrue(successful);
    }

    @Test
    public void shouldThrowExceptionWhenSendMessage() {
        //given
        Mockito.doThrow(new MailSendException("Failed")).when(mockClient).sendEmail(any(SendEmailRequest.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertFalse(successful);
    }
}