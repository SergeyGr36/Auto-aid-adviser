package com.hillel.evo.adviser.filter;

import com.hillel.evo.adviser.service.JwtService;
import com.hillel.evo.adviser.service.SecurityUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final transient JwtService jwtService;
    private final transient SecurityUserDetailsService detailsService;

    @Autowired
    public JwtAuthorizationFilter(final JwtService jwtService, SecurityUserDetailsService detailsService) {
        this.jwtService = jwtService;
        this.detailsService = detailsService;
    }

    @Override
    public void doFilterInternal(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final FilterChain filterChain) throws IOException, ServletException {

        final String accessToken = jwtService.getTokenFromRequest(request);

        Long userId = jwtService.getUserIdFromToken(accessToken);

        if (userId != null) {

            Authentication trustedAuthentication = detailsService.createTrustedAuthenticationWithUserId(userId);

            SecurityContextHolder.getContext().setAuthentication(trustedAuthentication);

        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
