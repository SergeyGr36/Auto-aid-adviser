package com.hillel.evo.adviser.service.mapper;

import com.hillel.evo.adviser.RegistrationApplication;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.mapper.SimpleUserMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RegistrationApplication.class})
public class SimpleUserMapperTest {

    @Autowired
    private transient SimpleUserMapperImpl simpleUserMapper;

    @Test
    public void toDtoReturnDto() {
        SimpleUser simpleUser = getSimpleUser();
        //when
        SimpleUserDto simpleUserDto = simpleUserMapper.toDto(simpleUser);
        //then
        assertEquals(simpleUser.getId(), simpleUserDto.getId());
        assertEquals(simpleUser.getFirstName(), simpleUserDto.getFirstName());
        assertEquals(simpleUser.getLastName(), simpleUserDto.getLastName());
    }

    @Test
    public void toDtoReturnNull() {
        SimpleUserDto simpleUserDto = simpleUserMapper.toDto(null);
        //then
        assertNull(simpleUserDto);
    }

    private SimpleUser getSimpleUser() {
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setId(1L);
        simpleUser.setFirstName("First name");
        simpleUser.setLastName("Last name");
        simpleUser.setPhone("0123456");

        return simpleUser;
    }
}
