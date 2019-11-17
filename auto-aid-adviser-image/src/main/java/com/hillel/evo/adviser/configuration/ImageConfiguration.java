package com.hillel.evo.adviser.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "image", name = "service", havingValue = "aws")
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard().build();
    }

    //Only to start context, wont work locally
    @Bean
    @ConditionalOnProperty(prefix = "image", name = "service", havingValue = "default")
    public AmazonS3 mockS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
    }

}
