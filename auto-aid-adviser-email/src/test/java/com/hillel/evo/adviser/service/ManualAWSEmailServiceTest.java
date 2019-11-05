package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test is intended to be run manually using your IDE
 * 1. Set OS environment variable AWS_REGION with value "eu-west-1". May be you need to restart your IDE.
 * 2. Create folder ".aws" in your user home folder
 * 3. Create file with name "credentials" inside the folder
 * 4. Put following three lines in the file, using your real AWS credentials instead of "XXX"
 * [default]
 * aws_access_key_id=XXXXXXXXXXXXXXXXXXXX
 * aws_secret_access_key=XXXXXXXXXXXXXXXXXXXXXXXX
 * 5. Open test\resources\application-dev.yml in this module and set a property:
 * spring-mail-username (your some mailbox)
 * 6. Verify mailboxes that you use in this message using AWS SES service
 * 7. Run the test
 * 8. If test passed check your mailbox)
 */
@Disabled
@SpringBootTest
@ActiveProfiles("dev")
public class ManualAWSEmailServiceTest {
    @Autowired
    AWSEmailService service;

    @Test
    public void doTest() {
        MessageParameters parameters = MessageParameters.builder()
                .toAddresses("somemailbox@ukr.net")
                .subject("AWS mail service test")
                .text("Please use email client with HTML support.")
                .html("<H4>If you receive this message, AWS mail service test was successful.</H4>")
                .build();
        boolean result = service.sendMessage(parameters);
        assertTrue(result);
    }
}
