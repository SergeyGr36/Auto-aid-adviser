package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;

import java.util.Map;

public interface TemplateService {
    String convert(String nameOfTemplate, String source, Map<String, String> parameters);
    String convert(MessageParameters messageParameters, EmailContentType type);
}
