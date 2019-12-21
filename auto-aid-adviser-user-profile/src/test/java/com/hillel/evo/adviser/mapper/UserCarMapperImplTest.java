package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.entity.TypeCar;
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
import java.util.HashSet;
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
        UserCar car = getTestUserCar();
        UserCarDto dto = mapper.toDto(car);
        assertEquals(car.getCarModel().getName(), dto.getCarModel().getName());
    }

    //    to dto list
    @Test
    public void WhenSetListThenReturnDto() {
        //given
        UserCar car = getTestUserCar();
        List<UserCar> userCars = new ArrayList<>();
        userCars.add(car);
        //when
        List<UserCarDto> userCarDtos = mapper.toDtoList(userCars);
        //then
        assertEquals(userCars.size(), userCarDtos.size());
        assertEquals(userCars.get(0).getCarModel().getName(), userCarDtos.get(0).getCarModel().getName());
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
        UserCarDto dto = getTestUserCarDto();
        UserCar car = mapper.toCar(dto, new SimpleUser());
        assertEquals(dto.getCarModel().getName(), car.getCarModel().getName());
    }

    @Test
    public void whenSetDtoCarWithUserNullThenReturnEntity() {
        UserCarDto dto = getTestUserCarDto();
        UserCar car = mapper.toCar(dto, null);
        assertNull(car.getSimpleUser());
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
        UserCarDto dto = getTestUserCarDto();
        UserCar car = mapper.toCar(dto, user);
        assertEquals(user.getLastName(), car.getSimpleUser().getLastName());
    }

    private UserCar getTestUserCar() {
        TypeCar typeCar = new TypeCar(1L, "TestType");
        CarBrand carBrand = new CarBrand(1L, "TestBrand");
        CarModel carModel = new CarModel(1L, "TestModel", carBrand, typeCar);
        SimpleUser simpleUser = new SimpleUser();

        UserCar car = new UserCar(99L, 2016, "AS123456", "some", simpleUser, carModel, new HashSet<>());

        return car;
    }

    private UserCarDto getTestUserCarDto(){
        TypeCarDto typeCar = new TypeCarDto(1L, "Легковой");
        CarBrandDto carBrand = new CarBrandDto(1L, "Mersedes");
        CarModelDto carModel = new CarModelDto(1L, "C-class", carBrand, typeCar);

        UserCarDto car = new UserCarDto();
        car.setCarModel(carModel);
        car.setReleaseYear(2016);
        return car;
    }
}
