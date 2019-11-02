package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.JwtPropertyConfiguration;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class JwtServiceMockTest {

    private final static String TEST_KEY = "test-key";
    private final static Long USER_ID = 100L;
    private final static Long EXPIRATION = 10000000L;

    @Mock private JwtPropertyConfiguration jwtProperties;
    @Mock HttpServletRequest request;
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtService = new JwtService(jwtProperties);
        when(jwtProperties.getSecretKey()).thenReturn(TEST_KEY);
        when(jwtProperties.getExpirationMillis()).thenReturn(EXPIRATION);
    }

    @Test
    public void whenGenerateAccessToken_thenTokenWithCorrectUserIdIsGenerated() {

        String token = jwtService.generateAccessToken(USER_ID);

        Long userIdFromToken = Long.parseLong(
                Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject());

        assertEquals(USER_ID, userIdFromToken);

    }

    @Test
    public void whenGetUserIdFromToken_thenCorrectUserIdIsReturned() {
        String token = jwtService.generateAccessToken(USER_ID);

        assertEquals(USER_ID, jwtService.getUserIdFromToken(token));
    }

    @Test
    public void getTokenFromRequest() {
        // given
        final String token = "token";
        // when
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(JwtService.TOKEN_PREFIX + token);
        //then
        assertEquals(token, jwtService.getTokenFromRequest(request));
    }
}