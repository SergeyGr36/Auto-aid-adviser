package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AWSEmailServiceTest {
    private static final EmailConfigurationProperties mockProperties = mock(EmailConfigurationProperties.class);
    private static final TemplateService mockTemplateService = mock(TemplateService.class);

    @Test
    void sendMessage() {
    }
}