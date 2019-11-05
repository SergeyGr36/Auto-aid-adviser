package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test is intended to be run manually using your IDE
 * 1. Generate "application password" for your GMail account
 * https://support.google.com/accounts/answer/185833?hl=en
 * May be Google will force you to turn on 2-step verification for GMail account
 * 2. Open test\resources\application.yml in this module and set two properties:
 * spring-mail-username (your Google mailbox)
 * spring-mail-password (your "application password")
 * 3. Run the test
 * 4. If test passed check your mailbox)
 */
@Disabled
@SpringBootTest
public class ManualDefaultEmailServiceTest {
    @Autowired
    DefaultEmailService service;

    @Test
    public void doTest() {
        MessageParameters parameters = MessageParameters.builder()
                .toAddresses("yourownmailbox@ukr.net")
                .nameOfTemplate("confirmation-of-registration")
                .templateParameter("userName", "USERNAME")
                .templateParameter("link", "www.google.com")
                .subject("Default mail service test")
                .build();
        boolean result = service.sendMessage(parameters);
        assertTrue(result);
    }
}
