package com.hillel.evo.adviser.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtPropertyConfiguration {

    @NotBlank
    private String secretKey;

    @NotBlank
    private Long expirationMillis;
}

