package com.hillel.evo.adviser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ThymeleafTemplateService implements TemplateService {
    private TemplateEngine templateEngine;

    @Autowired
    public ThymeleafTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String convert(String source) {
        Context context = new Context();
        context.setVariable("message", source);
        return templateEngine.process("mailTemplate", context);
    }
}
