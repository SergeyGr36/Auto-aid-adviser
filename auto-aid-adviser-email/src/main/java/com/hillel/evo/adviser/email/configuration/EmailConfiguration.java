package com.hillel.evo.adviser.email.configuration;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "email", name = "service", havingValue = "aws")
public class EmailConfiguration {
    @Bean
    public AmazonSimpleEmailService amazonMailService() {
        AmazonSimpleEmailService client =
        AmazonSimpleEmailServiceClientBuilder.standard()
        // Replace US_WEST_2 with the AWS Region you're using for
        // Amazon SES.
        //.withRegion(Regions.US_WEST_2)
        .build();

        return client;
    }
}
