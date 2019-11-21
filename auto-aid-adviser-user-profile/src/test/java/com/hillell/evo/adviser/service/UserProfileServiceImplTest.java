package com.hillell.evo.adviser.service;

import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillell.evo.adviser.dto.UserProfileDto;
import com.hillell.evo.adviser.repository.UserProfileRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(value = {"/clean-user-profile.sql", "/user-profile.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserProfileServiceImplTest {
    private UserProfileDto dto;
    @Autowired
    private UserProfileServiceImpl service;
    @Autowired
    private UserProfileRepository repository;
    @BeforeEach
    private void createDto(){
        dto.setId(1L);
        dto.setUser(new SimpleUser());
    }
    @Test
    public void whenCreateUserProfileThenReturnDto(){
        SimpleUser user = new SimpleUser();
        UserProfileDto test = service.createUserProfile(dto, user);
       assertEquals(dto.getId(), test.getId());
    }
}
