package com.hillel.evo.adviser.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TemplateService templateService;

    @Autowired
    public AWSEmailService(AmazonSimpleEmailService client,
                           EmailConfigurationProperties emailProperties,
                           TemplateService templateService) {
        this.client = client;
        this.emailProperties = emailProperties;
        this.templateService = templateService;
    }

    @Override
    public boolean sendMessage(MessageParameters params) {
        try {
            String text = templateService.convert(params, EmailContentType.TEXT);
            String html = templateService.convert(params, EmailContentType.HTML);

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(params.getToAddresses()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(html))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(text)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(params.getSubject())))
                    .withSource(emailProperties.getUsername());
            client.sendEmail(request);
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }
}
