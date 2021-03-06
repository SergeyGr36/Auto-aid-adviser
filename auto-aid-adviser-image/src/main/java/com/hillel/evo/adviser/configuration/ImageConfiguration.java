package com.hillel.evo.adviser.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class ImageConfiguration {

    private final ImageConfigurationProperties properties;

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(properties.getRegion())
                .build();
    }

    @Bean
    public TransferManager transferManager() {
        return TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3Client())
                .build();
    }
}
