package com.hillel.evo.adviser.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.hillel.evo.adviser")
@EntityScan("com.hillel.evo.adviser")
@EnableSwagger2
public class AdviserStarter {
    public static void main(String[] args) {
        SpringApplication.run(AdviserStarter.class, args);
    }
}
