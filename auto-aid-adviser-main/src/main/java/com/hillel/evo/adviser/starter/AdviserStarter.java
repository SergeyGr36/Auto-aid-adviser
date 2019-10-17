package com.hillel.evo.adviser.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.hillel.evo.adviser")
public class AdviserStarter {
    public static void main(String[] args) {
        SpringApplication.run(AdviserStarter.class, args);
    }
}
