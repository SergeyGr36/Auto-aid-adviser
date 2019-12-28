package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.TypeCarDto;
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
public class TypeCarMapperImplTest {

    @Autowired
    private TypeCarMapperImpl typeCarMapper;

    @Test
    public void toDtoSetNullThenReturnNull() {
        assertNull(typeCarMapper.toDto(null));
    }

    @Test
    public void toDtoThenReturnEntity() {
        TypeCar entity = getTestTypeCarEntity();
        TypeCarDto dto = typeCarMapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void toListDtoThenReturnListEntity() {
        TypeCar entity = getTestTypeCarEntity();
        List<TypeCarDto> dto = typeCarMapper.toListDto(Arrays.asList(entity));
        assertEquals(entity.getId(), dto.get(0).getId());
        assertEquals(entity.getName(), dto.get(0).getName());
    }

    @Test
    public void toEntitySetNullThenReturnNull() {
        assertNull(typeCarMapper.toEntity(null));
    }

    @Test
    public void toEntityThenReturnDto() {
        TypeCarDto dto = getTestTypeCarDto();
        TypeCar entity = typeCarMapper.toEntity(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    public void toDtoListNullThenReturnNull() {
        assertNull(typeCarMapper.toListDto(null));
    }

    @Test
    public void toListEntityThenReturnListDto() {
        TypeCarDto dto = getTestTypeCarDto();
        List<TypeCar> list = typeCarMapper.toListEntity(Arrays.asList(dto));
        assertEquals(dto.getId(), list.get(0).getId());
        assertEquals(dto.getName(), list.get(0).getName());
    }

    private TypeCarDto getTestTypeCarDto() {
        TypeCarDto typeCarDto = new TypeCarDto(1L, "test");
        return typeCarDto;
    }

    private TypeCar getTestTypeCarEntity() {
        TypeCar typeCar = new TypeCar(1L, "test");
        return typeCar;
    }
}
