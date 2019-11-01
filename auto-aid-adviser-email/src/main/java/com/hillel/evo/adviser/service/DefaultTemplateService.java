package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


@Service
public class DefaultTemplateService implements TemplateService {
    private TemplateEngine templateEngine;

    @Autowired
    public DefaultTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String convert(String nameOfTemplate, String source, Map<String, String> parameters) {
        if (source == null) return source;
        Context context = new Context();
        parameters.forEach((key, value) -> context.setVariable(key, value));
        return templateEngine.process(nameOfTemplate, context);
    }

    @Override
    public String convert(MessageParameters parameters, EmailContentType type) {
        return convert(parameters.getNameOfTemplate(),
                type == EmailContentType.HTML ? parameters.getHtml() : parameters.getText(),
                parameters.getTemplateParameters());
    }
}
