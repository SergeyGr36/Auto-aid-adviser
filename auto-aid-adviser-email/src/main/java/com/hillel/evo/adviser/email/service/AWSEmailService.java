package com.hillel.evo.adviser.email.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.hillel.evo.adviser.email.dto.MessageDto;
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

    @Override
    public void sendMessage(MessageDto dto) {
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            //.withRegion(Regions.US_WEST_2)
                            .build();
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
