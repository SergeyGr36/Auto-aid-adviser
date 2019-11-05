package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import org.thymeleaf.context.Context;
import org.thymeleaf.ITemplateEngine;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import com.hillel.evo.adviser.parameter.MessageParameters.MessageParametersBuilder;

class DefaultTemplateServiceTest {
    private static final ITemplateEngine mockTemplateEngine = mock(ITemplateEngine.class);
    private static final TemplateService service = new DefaultTemplateService(mockTemplateEngine);
    private static MessageParameters parameters;
    private static MessageParametersBuilder builder;
    private static final String testString = "Test";
    private static final String testNameOfTemplate = "some-template.html";

    @BeforeAll
    static void setUp() {
        doReturn(testString).when(mockTemplateEngine).process(eq(testNameOfTemplate), any(Context.class));
        builder = MessageParameters.builder()
                .toAddresses("some@ukr.net")
                .ccAddresses("some@gmail.com")
                .bccAddresses("another@ukr.net")
                .subject(testString)
                .text(testString)
                .html(testString);
    }

    @Test
    void shouldConvertWithTemplate() {
        //given
        parameters = builder.nameOfTemplate(testNameOfTemplate)
                .templateParameter("userName", "Obama")
                .build();

        //when
        String result = service.convert(parameters, EmailContentType.HTML);

        //then
        assertEquals(testString, result);
    }

    @Test
    void shouldConvertWithoutTemplate() {
        //given
        parameters = builder.nameOfTemplate(null)
                .build();

        //when
        String result = service.convert(parameters, EmailContentType.TEXT);

        //then
        assertEquals(testString, result);
    }
}