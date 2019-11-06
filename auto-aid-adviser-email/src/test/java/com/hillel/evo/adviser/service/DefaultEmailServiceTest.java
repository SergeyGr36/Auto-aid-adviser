package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import com.hillel.evo.adviser.parameter.MessageParameters.MessageParametersBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

public class DefaultEmailServiceTest {
    private static final JavaMailSender mockSender = mock(JavaMailSender.class);
    private static final EmailConfigurationProperties mockProperties = mock(EmailConfigurationProperties.class);
    private static final MimeMessage mockMessage = mock(MimeMessage.class);
    private static final TemplateService mockTemplateService = mock(TemplateService.class);
    private static final String testString = "Test";
    private static MessageParameters parameters;
    private static MessageParametersBuilder builder = MessageParameters.builder();

    private EmailService service = new DefaultEmailService(mockSender, mockProperties, mockTemplateService);

    @BeforeAll
    static void setUp() {
        builder.toAddresses("some@ukr.net");
        when(mockSender.createMimeMessage()).thenReturn(mockMessage);
        when(mockProperties.getUsername()).thenReturn("Anonimous");
        when(mockTemplateService.convert(any(MessageParameters.class), any(EmailContentType.class))).thenReturn(testString);
    }

    @Test
    public void shouldReturnTrueWhenSendMessageWithTemplate() {
        //given
        setAdditionalBuilderFields();
        parameters = builder.nameOfTemplate("some-template.html")
                .templateParameter("userName", "Obama")
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
        setAdditionalBuilderFields();
        parameters = builder.nameOfTemplate(null).build();
        Mockito.doNothing().when(mockSender).send(any(MimeMessage.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertTrue(successful);
    }

    @Test
    public void shouldReturnTrueWhenSendEmptyMessage() {
        //given
        clearAdditionalBuilderFields();
        parameters = builder.build();
        Mockito.doNothing().when(mockSender).send(any(MimeMessage.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertTrue(successful);
    }

    @Test
    public void shouldThrowExceptionWhenSendMessage() {
        //given
        setAdditionalBuilderFields();
        parameters = builder.build();
        Mockito.doThrow(new MailSendException("Failed")).when(mockSender).send(any(MimeMessage.class));

        //when
        boolean successful = service.sendMessage(parameters);

        //then
        assertFalse(successful);
    }

    private static void setAdditionalBuilderFields() {
        builder.ccAddresses("some@gmail.com")
                .bccAddresses("another@ukr.net")
                .subject(testString)
                .text(testString)
                .html(testString);
    }

    private static void clearAdditionalBuilderFields() {
        builder.ccAddresses(null)
                .bccAddresses(null)
                .subject(null)
                .text(null)
                .html(null);
    }
}
