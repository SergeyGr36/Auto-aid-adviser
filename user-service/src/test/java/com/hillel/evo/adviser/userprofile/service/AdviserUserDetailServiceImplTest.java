package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;
import com.hillel.evo.adviser.userprofile.exception.ResourceNotFoundException;
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
public class AdviserUserDetailServiceImplTest {

    @Autowired
    private AdviserUserDetailService service;

    @Test
    public void activation_returnAUD() {
        //given
        final String activeCode = "asdf-1234-simple";
        //when
        AdviserUserDetails aud = service.activation(activeCode);
        //then
        Assert.assertNull(aud.getActivationCode());
        Assert.assertEquals(aud.isActive(), true);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void activation_returnThrow() {
        //given
        final String activeCode = "asdf-1234-xxxxx";
        //when
        AdviserUserDetails aud = service.activation(activeCode);
    }

}