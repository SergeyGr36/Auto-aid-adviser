package com.hillel.evo.adviser.email.service;

import com.hillel.evo.adviser.email.dto.MessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {DefaultEmailServiceTest.MyTestConfiguration.class})
public class DefaultEmailServiceTest {
    //private static final JavaMailSender mockSender = mock(JavaMailSender.class);
    @Autowired
    DefaultEmailService service;// = new DefaultEmailService(new JavaMailSenderImpl());
    @Test
    public void sendMessage() {
        System.out.println("Service: " + service);
        MessageDto dto = new MessageDto.Builder().setToAddresses("dir2000@ukr.net").build();
        service.sendMessage(dto);
    }

    @TestConfiguration
    @ComponentScan()
    class MyTestConfiguration {

    }
}
