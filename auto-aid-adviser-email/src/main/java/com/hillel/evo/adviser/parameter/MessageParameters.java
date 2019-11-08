package com.hillel.evo.adviser.parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class MessageParameters {
    private String toAddresses;
    private String ccAddresses;
    private String bccAddresses;

    private String subject;
    private String text;
    private String html;
    private String nameOfTemplate;
    @Singular
    private Map<String, String> templateParameters;
}
