package com.hillel.evo.adviser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdviserStarter {
    public static void main(String[] args) {
        int a = 1;
        System.out.println(a);
        SpringApplication.run(AdviserStarter.class, args);
    }
}
