package com.hillel.evo.adviser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.hillel.evo.adviser")
public class SearchApp {

    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }
}