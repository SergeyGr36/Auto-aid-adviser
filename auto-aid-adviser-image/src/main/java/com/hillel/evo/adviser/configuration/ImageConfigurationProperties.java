package com.hillel.evo.adviser.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws.s3")
public class ImageConfigurationProperties {
    private String bucketName;
    private Long expiresIn;
    private String region;
}
