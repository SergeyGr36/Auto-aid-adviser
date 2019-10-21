package com.hillel.evo.adviser.userprofile.service;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SimpleUserServiceImplTest {

    @Autowired
    private SimpleUserServiceImpl service;

    @Test
    public void registration_returnAUD() {
        //given
        SimpleUserRegistrationDto dto = new SimpleUserRegistrationDto("test@mail.com", "12345678");
        //when
        AdviserUserDetails aud = service.registration(dto);
        //then
        Assert.assertEquals(aud.getEmail(), dto.getEmail());
        Assert.assertNotNull(aud.getActivationCode());
        Assert.assertEquals(aud.isActive(), false);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void registration_returnThrow() {
        //given
        SimpleUserRegistrationDto dto = new SimpleUserRegistrationDto("bvg@mail.com", "12345678");
        //when
        AdviserUserDetails adviserUserDetails = service.registration(dto);
    }
}