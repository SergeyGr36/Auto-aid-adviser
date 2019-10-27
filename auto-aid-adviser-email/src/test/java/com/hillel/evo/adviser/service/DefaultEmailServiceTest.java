package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.parameters.MessageParameters;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class DefaultEmailServiceTest {
    //private static final JavaMailSender mockSender = mock(JavaMailSender.class);
    @Autowired
    EmailService service;
    @Test
    public void sendMessage() {
        System.out.println("Service: " + service);
        MessageParameters dto = new MessageParameters.Builder()
                .setToAddresses("dir2000@ukr.net")
                .setCcAddresses("formica277@gmail.com")
                .setBccAddresses("dir2000@ukr.net")
                .setSubject("Test")
                .setText("Test")
                .setHtml("<H1>Test</H1>")
                .addtAttachment("E:/Avant.png")
                .build();
        service.sendMessage(dto);
    }
}
