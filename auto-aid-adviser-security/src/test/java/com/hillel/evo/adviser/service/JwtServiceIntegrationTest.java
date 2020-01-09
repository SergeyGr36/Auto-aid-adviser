package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.SecurityAppStarter;
import com.hillel.evo.adviser.configuration.JwtPropertyConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SecurityAppStarter.class)
public class JwtServiceIntegrationTest {

    private final static Long USER_ID = 10L;

    @Autowired
    JwtService jwtService;

    @Autowired
    JwtPropertyConfiguration jwtProperties;

    @Test
    public void whenTokenIsMalformed_thenIsTokenValidFalse() {

        assertFalse(jwtService.isTokenValid("malformedToken"));
    }

    @Test
    public void whenTokenIsValid_thenIsTokenValidTrue() {

        String token = jwtService.generateAccessToken(USER_ID);

        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    public void whenSubjectStringParsesToLong_thenGetSubjectFromTokenReturnNotNull() {

        String token = Jwts.builder()
                .setSubject(USER_ID.toString())
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();

        Assertions.assertNotNull(jwtService.getUserIdFromToken(token));
    }


    @Test
    public void whenSubjectStringNotParsesToLong_thenGetSubjectFromTokenReturnNull() {

        String token = Jwts.builder()
                .setSubject("NotLong")
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();

        Assertions.assertNull(jwtService.getUserIdFromToken(token));
    }

    @Test
    public void whenNoSubject_thenGetSubjectFromTokenReturnNull() {

        String token = Jwts.builder()
                .setPayload("no subject")
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();

        Assertions.assertNull(jwtService.getUserIdFromToken(token));
    }

}
