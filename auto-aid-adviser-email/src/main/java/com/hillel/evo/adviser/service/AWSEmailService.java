package com.hillel.evo.adviser.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.hillel.evo.adviser.parameters.MessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@ConditionalOnProperty(prefix = "email", name = "service", havingValue = "aws")
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class AWSEmailService implements EmailService {
    @Value("${spring.mail.username}")
    private String userName;
    private AmazonSimpleEmailService client;

    @Autowired
    public AWSEmailService(AmazonSimpleEmailService client) {
        this.client = client;
    }

    @Override
    public void sendMessage(MessageParameters dto) {
        try {
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(dto.getToAddresses()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(dto.getHtml()))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(dto.getText())))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(dto.getSubject())))
                    .withSource(userName);
            // Comment or remove the next line if you are not using a
            // configuration set
            //.withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
