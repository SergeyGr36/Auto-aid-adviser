package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.SecurityAppStarter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final String USER_NAME = "test@gmail.com";
    private final String PASSWORD = "testtest123";

    @Test
    public void whenLoadUserByUsername_thenUserSuccessfullyLoaded() {

        UserDetails user = userDetailsService.loadUserByUsername(USER_NAME);

        Assertions.assertEquals(USER_NAME, user.getUsername());
        Assertions.assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void whenUserNotFound_thenThrowsUsernameNotFoundException() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("no such username"));
    }

}