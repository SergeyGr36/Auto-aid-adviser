package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.mapper.UserCarMapper;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.UserCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserCarServiceImplTest {

    private Long userId;
    private UserCarDto dto;

    @Autowired
    private UserCarService service;
    @Autowired
    private UserCarRepository repository;
    @Autowired
    private AdviserUserDetailRepository userRepository;
    @Autowired
    private UserCarMapper mapper;

    @BeforeEach
    private void createDto(){
        userId = userRepository.findByEmail("svg@mail.com").get().getId();
    }

    @Test
    public void whenCreateUserCarThenReturnDto(){
        UserCarDto newDto = new UserCarDto();
        UserCarDto testDto = service.createUserCar(newDto, userId);
       assertNotNull(testDto.getId());
    }

    @Test
    public void whenUpdateUserCarThenReturnDto(){
        dto = service.getByUserId(userId).get(0);
        UserCarDto testDto = service.updateUserCar(dto, userId);
        assertEquals(dto.getId(), testDto.getId());
    }
    @Test
    public void whenGetCarByUserIdAndCarIdThenReturnDto(){
        UserCar car = new UserCar();
        UserCarDto actual = service.createUserCar(mapper.toDto(car), userId);
        UserCarDto expected = service.getCarByUserIdAndCarId(userId, actual.getId());
        assertEquals(actual.getId(), expected.getId());
    }
    @Test
    public void whenDeleteUserCarThenThrowException(){
        assertThrows(RuntimeException.class, ()-> service.deleteUserCar(100L, userId) );
    }
    @Test
    public void whenDeleteUserCarThenSuccess(){
        UserCarDto dto = service.createUserCar(new UserCarDto(), userId);
      Assertions.assertDoesNotThrow(()->service.deleteUserCar(dto.getId(), userId));

    }
}
