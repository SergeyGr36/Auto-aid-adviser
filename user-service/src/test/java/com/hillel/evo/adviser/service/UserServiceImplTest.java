package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        AdviserUserDetails aud = service.activation(activeCode).get();
        //then
        assertNull(aud.getActivationCode());
        assertTrue(aud.isActive());
    }

    @Test
    public void activation_returnNull() {
        //given
        final String activeCode = "asdf-1234-xxxxx";
        //when
        AdviserUserDetails aud = service.activation(activeCode).orElse(null);
        //then
        assertNull(aud);
    }

    @Test
    public void registration_business_returnAUD() {
        //given
        BusinessUserRegistrationDto dto = new BusinessUserRegistrationDto();
        dto.setEmail("test@mail.com");
        dto.setPassword("12345678");
        //when
        Optional<AdviserUserDetails> optional = service.registration(dto);
        //then
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getEmail(), dto.getEmail());
        assertNotNull(optional.get().getActivationCode());
        assertEquals(optional.get().isActive(), false);
    }

    @Test
    public void registration_business_returnEmptyOptital() {
        //given
        BusinessUserRegistrationDto dto = new BusinessUserRegistrationDto();
        dto.setEmail("bvg@mail.com");
        dto.setPassword( "12345678");
        //when
        Optional<AdviserUserDetails> optional = service.registration(dto);
        //then
        assertFalse(optional.isPresent());
    }

    @Test
    public void registration_simple_returnAUD() {
        //given
        SimpleUserRegistrationDto dto = new SimpleUserRegistrationDto();
        dto.setEmail("test@mail.com");
        dto.setPassword("12345678");
        //when
        Optional<AdviserUserDetails> optional = service.registration(dto);
        //then
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getEmail(), dto.getEmail());
        assertNotNull(optional.get().getActivationCode());
        assertEquals(optional.get().isActive(), false);
    }

    @Test
    public void registration_simple_returnEmptyOptital() {
        //given
        SimpleUserRegistrationDto dto = new SimpleUserRegistrationDto();
        dto.setEmail("svg@mail.com");
        dto.setPassword( "12345678");
        //when
        Optional<AdviserUserDetails> optional = service.registration(dto);
        //then
        assertFalse(optional.isPresent());
    }
}