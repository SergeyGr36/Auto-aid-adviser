package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.TypeCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
public class CarBrandMapperImplTest {

    @Autowired
    private CarBrandMapperImpl mapper;

    @Test
    public void toDtoSetNullThenReturnNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void toDtoThenReturnEntity() {
        CarBrand entity = getTestCarBrandEntity();
        CarBrandDto dto = mapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void toListDtoThenReturnListEntity() {
        CarBrand entity = getTestCarBrandEntity();
        List<CarBrandDto> dto = mapper.toListDto(Arrays.asList(entity));
        assertEquals(entity.getId(), dto.get(0).getId());
        assertEquals(entity.getName(), dto.get(0).getName());
    }

    @Test
    public void toEntitySetNullThenReturnNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    public void toEntityThenReturnDto() {
        CarBrandDto dto = getTestCarBrandDto();
        CarBrand entity = mapper.toEntity(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    public void toDtoListNullThenReturnNull() {
        assertNull(mapper.toListDto(null));
    }

    @Test
    public void toListEntityThenReturnListDto() {
        CarBrandDto dto = getTestCarBrandDto();
        List<CarBrand> list = mapper.toListEntity(Arrays.asList(dto));
        assertEquals(dto.getId(), list.get(0).getId());
        assertEquals(dto.getName(), list.get(0).getName());
    }

    private CarBrandDto getTestCarBrandDto() {
        CarBrandDto dto = new CarBrandDto(1L, "test");
        return dto;
    }

    private CarBrand getTestCarBrandEntity() {
        CarBrand entity = new CarBrand(1L, "test");
        return entity;
    }
}
