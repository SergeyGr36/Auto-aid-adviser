package com.hillel.evo.adviser.security.utils;

import com.hillel.evo.adviser.security.property.JwtConfigurationProperties;
import com.hillel.evo.adviser.security.service.AutoAidUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    public static final String TOKEN_PREFIX = "Bearer ";

    private final transient JwtConfigurationProperties jwtProperties;

    @Autowired
    public JwtUtils(final JwtConfigurationProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(final String userName) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiresIn());
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    public String getUserNameFromToken(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getTokenFromRequest(final HttpServletRequest request) {

        final String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(jwt) && jwt.startsWith(TOKEN_PREFIX)) {
            return jwt.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Boolean tokenIsValid(final String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException
                | UnsupportedJwtException | IllegalArgumentException ex) {
            log.error("Validate token failed {}", "Wrong token");
            return false;
        }
    }
}

