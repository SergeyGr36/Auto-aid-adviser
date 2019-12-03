package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.mapper.UserCarMapperImpl;
import com.hillel.evo.adviser.repository.UserCarRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
@Sql(value = {"/clean-user-profile.sql", "/user-profile.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserCarMapperTest {
    @Autowired
    UserCarMapperImpl mapper;
    @Autowired
    UserCarRepository repository;
    @Test
    public void whenToDtoSetNullReturnNull(){
        assertNull(mapper.toDto(null));

    }
}
