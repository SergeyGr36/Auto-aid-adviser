package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultTemplateServiceTest {
    private static final TemplateEngine mockTemplateEngine = mock(TemplateEngine.class);
    private static final TemplateService service = new DefaultTemplateService(mockTemplateEngine);
    private static MessageParameters parameters;
    private static MessageParameters.Builder builder;
    private static final String testString = "Test";
    private static final String testNameOfTemplate = new String("some-template.html");

    @BeforeAll
    static void setUp() {
        doReturn(testString).when(mockTemplateEngine).process((String) argThat(Objects::nonNull), any(Context.class));
        //when(mockTemplateEngine.process(any(String.class), any(Context.class))).thenReturn(testString);
        builder = new MessageParameters.Builder()
                .setToAddresses("some@ukr.net")
                .setCcAddresses("some@gmail.com")
                .setBccAddresses("another@ukr.net")
                .setSubject(testString)
                .setText(testString)
                .setHtml(testString);
    }

    @Test
    void shouldConvertWithTemplate() {
        //given
        parameters = builder.setNameOfTemplate(testNameOfTemplate)
                .addTemplateParameter("userName", "Obama")
                .build();

        //when
        String result = service.convert(parameters, EmailContentType.HTML);

        //then
        assertNull(result);
    }

    @Test
    void convert1() {
    }
}