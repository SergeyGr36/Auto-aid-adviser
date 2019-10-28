package com.hillel.evo.adviser.email.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.hillel.evo.adviser.email.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.email.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@ConditionalOnProperty(prefix = "email", name = "service", havingValue = "aws")
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class AWSEmailService implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AWSEmailService.class);
    private final EmailConfigurationProperties emailProperties;
    private AmazonSimpleEmailService client;

    @Autowired
    public AWSEmailService(AmazonSimpleEmailService client, EmailConfigurationProperties emailProperties) {
        this.client = client;
        this.emailProperties = emailProperties;
    }

    @Override
    public boolean sendMessage(MessageDto dto) {
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
                    .withSource(emailProperties.getUsername());
            // Comment or remove the next line if you are not using a
            // configuration set
            //.withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
    }
}
