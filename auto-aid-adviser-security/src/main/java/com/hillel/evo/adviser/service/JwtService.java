package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.JwtPropertyConfiguration;
import com.hillel.evo.adviser.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class JwtService {

    public static final String TOKEN_PREFIX = "Bearer ";

    private final transient JwtPropertyConfiguration jwtProperties;

    @Autowired
    public JwtService(final JwtPropertyConfiguration jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Generates a jwt token with the user id and expiry date.
     * @param userId The user id to be written into token.
     * @param expirationMillis expiration time in milliseconds.
     * @return The generated token.
     */
    public String generateAccessToken(long userId, long expirationMillis) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + expirationMillis);
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * Parses the provided string as jwt token.
     * If the string is a valid token, and has the user id information, then user id is returned.
     * Otherwise exceptions are thrown, as described below.
     *
     * @param token the string to be a jwt token.
     * @return The user id, parsed from token.
     *
     * @throws io.jsonwebtoken.ExpiredJwtException  if the specified JWT is a Claims JWT
     * and the Claims has an expiration time before the time this method is invoked.
     * <p> {@link InvalidJwtTokenException}
     * - if the token is null, not a jwt, or invalid for any other reason.
     */
    public Long getUserIdFromToken(final String token) {

        final Claims claims = parseToken(token);
        final String userIdString = claims.getSubject();

        try {
            return Long.parseLong(userIdString);

        } catch (NumberFormatException e) {
            throw new InvalidJwtTokenException(e);
        }
    }

    /**
     * Reads the "Authorization" header from the request, and returns content without specific prefix.
     * @param request the servlet request to read.
     * @return If there is content with specific prefix in the header, returns content of the header without prefix.
     * Otherwise returns null.
     */
    public String getTokenFromRequest(final HttpServletRequest request) {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * Checks if this token is a valid jwt token.
     * @param token the compact token string.
     * @return true, if the token is valid. Otherwise exceptions are thrown.
     * @throws io.jsonwebtoken.ExpiredJwtException  if the specified JWT is a Claims JWT
     * and the Claims has an expiration time before the time this method is invoked.
     * <p> {@link InvalidJwtTokenException}
     * - if the token is null, not a jwt, or invalid for any other reason.
     */
    public boolean tokenIsValid(final String token) {
        parseToken(token);
        return true;
    }

    /**
     * Parses the provided string as jwt and returns the claims object.
     * If the token is invalid, thows exceptions.
     *
     * @param token the compact token string.
     * @return Claims object, if the token is valid.
     *
     * @throws io.jsonwebtoken.ExpiredJwtException  if the specified JWT is a Claims JWT
     * and the Claims has an expiration time before the time this method is invoked.
     * <p> {@link InvalidJwtTokenException}
     * - if the token is null, not a jwt, or invalid for any other reason.
     */
    private Claims parseToken(final String token) {
        try {
            return Jwts.parser().
                    setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();

        } catch (SignatureException | MalformedJwtException
                | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new InvalidJwtTokenException(ex);
        }
    }
}

