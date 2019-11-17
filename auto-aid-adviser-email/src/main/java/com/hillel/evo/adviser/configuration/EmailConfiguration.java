package com.hillel.evo.adviser.configuration;

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

        return AmazonSimpleEmailServiceClientBuilder.standard()
                .build();
    }
}
