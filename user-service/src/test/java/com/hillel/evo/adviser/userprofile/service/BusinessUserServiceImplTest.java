package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.BusinessUserRegistrationDto;
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
public class BusinessUserServiceImplTest {

    @Autowired
    private BusinessUserService service;

    @Test
    public void registration_returnAUD() {
        //given
        BusinessUserRegistrationDto dto = new BusinessUserRegistrationDto("test@mail.com", "12345678");
        //when
        AdviserUserDetails adviserUserDetails = service.registration(dto);
        //then
        Assert.assertEquals(adviserUserDetails.getEmail(), dto.getEmail());
        Assert.assertNotNull(adviserUserDetails.getActivationCode());
        Assert.assertEquals(adviserUserDetails.isActive(), false);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void registration_returnThrow() {
        //given
        BusinessUserRegistrationDto dto = new BusinessUserRegistrationDto("bvg@mail.com", "12345678");
        //when
        AdviserUserDetails adviserUserDetails = service.registration(dto);
    }

}