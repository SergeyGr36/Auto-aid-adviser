package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final transient AdviserUserDetailRepository repository;

    public SecurityUserDetailsService(AdviserUserDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdviserUserDetails user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));

        return createUserDetailsFrom(user);
    }

    /**
     * Creates the trusted authentication (isAuthenticated = true) for the user with provided id.
     * @param userId - The id of the trusted user, that was previously authenticated.
     *             For example, the bearer of jwt token or an activation code.
     * @return the trusted authentication object.
     */
    public Authentication createTrustedAuthenticationWithUserId(Long userId) {

        AdviserUserDetails user = repository.findById(userId).get();

        return createTrustedAuthenticationWithUser(user);
    }

    /**
     * Creates the trusted authentication (isAuthenticated = true) for the provided user.
     * @param user - The trusted user, that was previously authenticated.
     *             For example, the bearer of jwt token or an activation code.
     * @return the trusted authentication object.
     */
    private Authentication createTrustedAuthenticationWithUser(AdviserUserDetails user) {

        UserDetails userDetails = createUserDetailsFrom(user);

        UsernamePasswordAuthenticationToken trustedAuthentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

        return trustedAuthentication;
    }

    /**
     * Creates an instance of User Details interface with provided user
     * @param user the user object, for which the user details is created
     * @return
     */
    private UserDetails createUserDetailsFrom(AdviserUserDetails user) {
        return new SecurityUserDetails() {

            @Override
            public Long getUserId() {
                return user.getId();
            }

            private String userRole = user.getRole().toString();

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(new SimpleGrantedAuthority(userRole));
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return user.isActive();
            }
        };
    }
}
