package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.enums.RoleUser;
import com.hillel.evo.adviser.exception.UserAlreadyExistsRegistrationException;
import com.hillel.evo.adviser.exception.ActivationCodeFoundNoMatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @Test
    public void activation_returnAUD() {
        //given
        final String activeCode = "asdf-1234-simple";
        //when
        AdviserUserDetailsDto dto = service.activation(activeCode);
        //then
        assertNotNull(dto);
        assertEquals(dto.getRoleUser(), RoleUser.ROLE_USER);
    }

    @Test
    public void activation_returnThrows() {
        //given
        final String activeCode = "asdf-1234-xxxxx";
        //then
        assertThrows(ActivationCodeFoundNoMatchException.class, () -> service.activation(activeCode));
    }

    @Test
    public void registration_business_returnAUD() {
        //given
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("test@mail.com");
        registrationDto.setPassword("12345678");
        registrationDto.setRole(RoleUser.ROLE_BUSINESS);
        //when
        AdviserUserDetailsDto returnDto = service.registration(registrationDto);
        //then
        assertNotNull(returnDto);
        assertEquals(returnDto.getRoleUser(), RoleUser.ROLE_BUSINESS);
    }

    @Test
    public void registration_business_returnThrows() {
        //given
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("bvg@mail.com");
        registrationDto.setPassword( "12345678");
        registrationDto.setRole(RoleUser.ROLE_BUSINESS);
        //then
        assertThrows(UserAlreadyExistsRegistrationException.class, () -> service.registration(registrationDto));
    }

    @Test
    public void registration_simple_returnAUD() {
        //given
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("test@mail.com");
        registrationDto.setPassword("12345678");
        registrationDto.setRole(RoleUser.ROLE_USER);
        //when
        AdviserUserDetailsDto returnDto = service.registration(registrationDto);
        //then
        assertNotNull(returnDto);
        assertEquals(returnDto.getRoleUser(), RoleUser.ROLE_USER);
    }

    @Test
    public void registration_simple_returnThrows() {
        //given
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("svg@mail.com");
        registrationDto.setPassword( "12345678");
        registrationDto.setRole(RoleUser.ROLE_USER);
        //then
        assertThrows(UserAlreadyExistsRegistrationException.class, () -> service.registration(registrationDto));
    }

    @Test
    public void registration_validationMail() {
        //given
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("svg-mail.com");
        registrationDto.setPassword( "12345678");
        registrationDto.setRole(RoleUser.ROLE_USER);
        //then
        assertThrows(ConstraintViolationException.class, () -> service.registration(registrationDto));
    }

    @Test
    public void registration_validationPassword() {
        //given
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("svg-mail.com");
        registrationDto.setPassword( "123");
        registrationDto.setRole(RoleUser.ROLE_USER);
        //then
        assertThrows(ConstraintViolationException.class, () -> service.registration(registrationDto));
    }

}