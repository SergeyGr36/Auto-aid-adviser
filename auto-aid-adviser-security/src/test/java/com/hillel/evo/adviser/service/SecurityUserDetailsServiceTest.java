package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.SecurityAppStarter;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest()
@ContextConfiguration(classes = SecurityAppStarter.class)
@Import(SecurityUserDetailsService.class)
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SecurityUserDetailsServiceTest {

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    AdviserUserDetailRepository userRepository;

    private final String USER_NAME = "test@gmail.com";

    @Test
    public void whenLoadUserByUsername_thenUserDetailsReturnsUsernameAndPassword() {

        AdviserUserDetails user = userRepository.findByEmail(USER_NAME).get();
        SecurityUserDetails userDetails = (SecurityUserDetails) userDetailsService.loadUserByUsername(USER_NAME);

        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    public void whenLoadUserByUsername_thenUserDetailsReturnsUserId() {

        AdviserUserDetails user = userRepository.findByEmail(USER_NAME).get();
        SecurityUserDetails userDetails = (SecurityUserDetails) userDetailsService.loadUserByUsername(USER_NAME);

        Assertions.assertEquals(user.getId(), userDetails.getUserId());
    }


    @Test
    public void whenUserNotFound_thenThrowsUsernameNotFoundException() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("no such username"));
    }

}