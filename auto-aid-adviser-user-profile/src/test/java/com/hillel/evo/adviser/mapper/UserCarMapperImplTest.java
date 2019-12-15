package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.identification.MotorTypeDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
@Sql(value = {"/user-profile.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserCarMapperImplTest {

    @Autowired
    SimpleUserRepository repository;
    @Autowired
    UserCarMapperImpl mapper;

    //to dto
    @Test
    public void whenToDtoSetNullReturnNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void whenToDtoSetEntityReturnDto() {
        UserCar car = new UserCar();
        car.setBrand(new CarBrand(1L, "Audi"));
        UserCarDto dto = mapper.toDto(car);
        assertEquals(car.getBrand().getName(), dto.getBrand().getName());
    }

    //    to dto list
    @Test
    public void WhenSetListThenReturnDto() {
        UserCar car = new UserCar();
        car.setTypeCar(new TypeCar(1L, "coupe"));
        List<UserCar> userCars = new ArrayList<>();
        userCars.add(car);
        List<UserCarDto> userCarDtos = mapper.toDtoList(userCars);
        assertEquals(userCarDtos.get(0).getTypeCar().getName(), userCars.get(0).getTypeCar().getName());
    }

    @Test
    public void whenSetNullListThenReturnNullListDto() {
        Assertions.assertNull(mapper.toDtoList((List<UserCar>) null));
    }

    //    to entity
    @Test
    public void whenSetEmptyDtoThenReturnNull() {
        Assertions.assertNull(mapper.toCar(null, null));
    }

    @Test
    public void whenSetDtoCarThenReturnEntity() {
        UserCarDto dto = new UserCarDto();
        dto.setMotorType(new MotorTypeDto(2L, "Audi"));
        UserCar car = mapper.toCar(dto, new SimpleUser());
        assertEquals(dto.getMotorType().getName(), car.getMotorType().getName());
    }

    @Test
    public void whenSetDtoUserThenReturnEntity() {
        SimpleUser user = new SimpleUser();
        user.setLastName("erwsfs");
        UserCar car = mapper.toCar(new UserCarDto(), user);
        assertEquals(user.getLastName(), car.getSimpleUser().getLastName());
    }

    @Test
    public void whenSetFullDtoThenReturnEntity() {
        SimpleUser user = repository.findAll().get(0);
        UserCarDto dto = new UserCarDto();
        dto.setMotorType(new MotorTypeDto(2L, "Audi"));
        UserCar car = mapper.toCar(dto, user);
        assertEquals(user.getLastName(), car.getSimpleUser().getLastName());
    }

}
