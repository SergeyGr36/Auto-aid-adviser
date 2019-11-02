package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import org.thymeleaf.context.Context;
import org.thymeleaf.ITemplateEngine;

import static org.mockito.Mockito.*;

class DefaultTemplateServiceTest {
    private static final ITemplateEngine mockTemplateEngine = mock(ITemplateEngine.class);
    private static final TemplateService service = new DefaultTemplateService(mockTemplateEngine);
    private static MessageParameters parameters;
    private static MessageParameters.Builder builder;
    private static final String testString = "Test";
    private static final String testNameOfTemplate = new String("some-template.html");

    @BeforeAll
    static void setUp() {
        doReturn(testString).when(mockTemplateEngine).process(eq(testNameOfTemplate), any(Context.class));
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
        assertEquals(testString, result);
    }

    @Test
    void shouldConvertWithoutTemplate() {
        //given
        parameters = builder.setNameOfTemplate(null)
                .build();

        //when
        String result = service.convert(parameters, EmailContentType.TEXT);

        //then
        assertEquals(testString, result);
    }
}