package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.service.impl.SimpleUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SimpleUserServiceImplTest {

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private SimpleUserServiceImpl simpleUserService;

    @Test
    public void getSimpleUserByIdReturnDto() {
        //given
        SimpleUser simpleUser = simpleUserRepository.findAll().get(0);
        //when
        SimpleUserDto dto = simpleUserService.findById(simpleUser.getId());
        //then
        assertEquals(simpleUser.getFirstName(), dto.getFirstName());
    }

    @Test
    public void getSimpleUserByIdReturnException() {
        assertThrows(RuntimeException.class, () -> simpleUserService.findById(99L));
    }

    @Test
    public void updateSimpleUserByIdReturnDto() {
        //given
        SimpleUser simpleUser = simpleUserRepository.findAll().get(0);
        SimpleUserDto dto = new SimpleUserDto(simpleUser.getId(), "somefName", "somelName", "(096)231-32-32");
        //when
        SimpleUserDto updateDto = simpleUserService.update(dto);
        SimpleUser user = simpleUserRepository.findById(simpleUser.getId()).get();
        //then
        assertEquals(user.getId(), updateDto.getId());
        assertEquals(user.getFirstName(), updateDto.getFirstName());
        assertEquals(dto.getFirstName(), updateDto.getFirstName());
        assertNotNull(user.getUserDetails());
    }


}
