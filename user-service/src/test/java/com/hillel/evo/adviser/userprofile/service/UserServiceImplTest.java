package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;
import com.hillel.evo.adviser.userprofile.exception.ResourceAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
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
        Assert.assertNull(aud.getActivationCode());
        Assert.assertTrue(aud.isActive());
    }

    @Test
    public void activation_returnNull() {
        //given
        final String activeCode = "asdf-1234-xxxxx";
        //when
        AdviserUserDetails aud = service.activation(activeCode).orElse(null);
        //then
        Assert.assertNull(aud);
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
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(optional.get().getEmail(), dto.getEmail());
        Assert.assertNotNull(optional.get().getActivationCode());
        Assert.assertEquals(optional.get().isActive(), false);
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
        Assert.assertFalse(optional.isPresent());
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
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(optional.get().getEmail(), dto.getEmail());
        Assert.assertNotNull(optional.get().getActivationCode());
        Assert.assertEquals(optional.get().isActive(), false);
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
        Assert.assertFalse(optional.isPresent());
    }
}