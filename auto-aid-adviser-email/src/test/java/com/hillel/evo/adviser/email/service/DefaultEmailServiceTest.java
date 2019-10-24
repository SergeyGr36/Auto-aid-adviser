package com.hillel.evo.adviser.email.service;

import com.hillel.evo.adviser.email.dto.MessageDto;
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
        MessageDto dto = new MessageDto.Builder().setToAddresses("dir2000@ukr.net").build();
        service.sendMessage(dto);
    }
}
