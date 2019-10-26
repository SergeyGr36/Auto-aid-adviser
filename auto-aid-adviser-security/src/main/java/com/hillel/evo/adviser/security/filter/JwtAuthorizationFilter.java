package com.hillel.evo.adviser.security.filter;


import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.security.handler.JwtEntryPointUnauthorizedHandler;
import com.hillel.evo.adviser.security.service.SecurityUserDetailsService;
import com.hillel.evo.adviser.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final transient AdviserUserDetailRepository repository;
    private final transient JwtService jwtService;
    private final transient SecurityUserDetailsService detailsService;

    @Autowired
    public JwtAuthorizationFilter(AdviserUserDetailRepository repository,
                                  final JwtService jwtService,
                                  final SecurityUserDetailsService detailsService,
                                  final JwtEntryPointUnauthorizedHandler authEntryPoint) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.detailsService = detailsService;
    }

    @Override
    public void doFilterInternal(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final FilterChain filterChain) throws IOException, ServletException {

        final String accessToken = jwtService.getTokenFromRequest(request);

        Long id = jwtService.getUserIdFromToken(accessToken);

        AdviserUserDetails user = repository.getOne(id);

        UserDetails userDetails = detailsService.loadUserByUsername(user.getEmail());

        setSecurityContext(userDetails);

        filterChain.doFilter(request, response);
    }

    /**
     * Creates a trusted Authentication object (isAuthenticated = true) from userDetails
     * and writes it to the security context.
     * @param userDetails
     */
    private void setSecurityContext(UserDetails userDetails) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
