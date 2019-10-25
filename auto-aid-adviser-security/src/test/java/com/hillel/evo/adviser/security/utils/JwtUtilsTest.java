package com.hillel.evo.adviser.security.utils;

import com.hillel.evo.adviser.security.configuration.JwtPropertyConfiguration;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class JwtUtilsTest {

    private final static String TEST_KEY = "test-key";
    private final static Long USER_ID = 100L;
    private static final long EXPIRATION = 3_600_000L;

    @Mock private JwtPropertyConfiguration jwtProperties;
    @Mock HttpServletRequest request;
    private JwtUtils jwtUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtUtils = new JwtUtils(jwtProperties);
        when(jwtProperties.getSecretKey()).thenReturn(TEST_KEY);
    }

    @Test
    public void whenGenerateAccessToken_thenTokenWithCorrectUserIdIsGenerated() {

        String token = jwtUtils.generateAccessToken(USER_ID, EXPIRATION);

        Long userIdFromToken = Long.parseLong(
                Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject());

        Assert.assertEquals(USER_ID, userIdFromToken);

    }

    @Test
    public void whenGetUserIdFromToken_thenCorrectUserIdIsReturned() {
        String token = jwtUtils.generateAccessToken(USER_ID, EXPIRATION);

        assertEquals(USER_ID, jwtUtils.getUserIdFromToken(token));
    }

    @Test
    public void getTokenFromRequest() {
        // given
        final String token = "token";
        // when
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(JwtUtils.TOKEN_PREFIX + token);
        //then
        assertEquals(token, jwtUtils.getTokenFromRequest(request));
    }

    @Test
    public void tokenIsValid() {
    }
}