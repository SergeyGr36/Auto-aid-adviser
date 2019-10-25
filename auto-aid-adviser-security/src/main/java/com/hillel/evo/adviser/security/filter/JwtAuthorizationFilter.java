package com.hillel.evo.adviser.security.filter;


import com.hillel.evo.adviser.security.handler.JwtEntryPointUnauthorizedHandler;
import com.hillel.evo.adviser.security.service.SecurityUserDetailsService;
import com.hillel.evo.adviser.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final transient JwtService jwtService;
    private final transient SecurityUserDetailsService detailsService;
    private final transient JwtEntryPointUnauthorizedHandler authEntryPoint;

    @Autowired
    public JwtAuthorizationFilter(final JwtService jwtService,
                                  final SecurityUserDetailsService detailsService,
                                  final JwtEntryPointUnauthorizedHandler authEntryPoint) {
        this.jwtService = jwtService;
        this.detailsService = detailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    public void doFilterInternal(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final FilterChain filterChain) throws IOException, ServletException {

        final String accessToken = jwtService.getTokenFromRequest(request);

        if (StringUtils.hasText(accessToken) && jwtService.tokenIsValid(accessToken)) {

            Long id = jwtService.getUserIdFromToken(accessToken);
            UserDetails userDetails = detailsService.loadUserByUsername("username");

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
