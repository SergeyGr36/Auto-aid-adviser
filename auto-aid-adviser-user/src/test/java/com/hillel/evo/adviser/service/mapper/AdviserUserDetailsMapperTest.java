package com.hillel.evo.adviser.service.mapper;

import com.hillel.evo.adviser.RegistrationApplication;
import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.mapper.AdviserUserDetailsMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RegistrationApplication.class})
public class AdviserUserDetailsMapperTest {

    @Autowired
    private transient AdviserUserDetailsMapperImpl adviserUserDetailsMapper;

    @Test
    public void toDtoReturnDto() {
        AdviserUserDetails userDetails = getAdviserUserDetails();
        AdviserUserDetailsDto adviserUserDetailsDto = adviserUserDetailsMapper.toDto(userDetails);
        Assertions.assertEquals(userDetails.getId(), adviserUserDetailsDto.getId());
    }

    @Test
    public void toDtoReturnNull() {
        AdviserUserDetailsDto adviserUserDetailsDto = adviserUserDetailsMapper.toDto(null);
        Assertions.assertNull(adviserUserDetailsDto);
    }

    private AdviserUserDetails getAdviserUserDetails() {
        AdviserUserDetails userDetails = new AdviserUserDetails();
        userDetails.setId(1L);
        userDetails.setEmail("mail@mail.com");

        return userDetails;
    }
}
