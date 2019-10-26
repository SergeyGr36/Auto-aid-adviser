package com.hillel.evo.adviser.security.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testContext() {
        System.out.println("test");

        Assertions.assertNotNull(context);

    }

}