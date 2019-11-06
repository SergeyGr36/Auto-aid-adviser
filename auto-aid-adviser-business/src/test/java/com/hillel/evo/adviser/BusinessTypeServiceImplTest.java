package com.hillel.evo.adviser;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.BusinessTypeMapper;
import com.hillel.evo.adviser.service.impl.BusinessTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessTypeServiceImplTest {
    @Autowired
    private BusinessTypeServiceImpl businessTypeService;
    @Autowired
    private BusinessTypeMapper mapper;

    @Test
    public void tryToDeleteThenThrowException() {
        assertThrows(DeleteException.class, () -> businessTypeService.deleteBusinessType(5L));
    }

    @Test
    public void whenGetTypeServiceByIdThenReturnThisOne() {
        BusinessType type = new BusinessType();
        final BusinessTypeDto dto = businessTypeService.getBusinessTypeById(1L);
        type = mapper.toType(dto);
        assertEquals(type.getType(), dto.getType());
        assertEquals(type.getId(), dto.getId());
    }
    @Test
    public void whenFindAllThenReturnThisList(){
        List<BusinessType> type;
        List<BusinessTypeDto> dto = businessTypeService.findAll();
        type = mapper.toAllType(dto);
        for (int i = 0; i <type.size()&&i<dto.size() ; i++) {
                assertEquals(type.get(i).getType(), dto.get(i).getType());
                assertEquals(type.get(i).getId(), dto.get(i).getId());
        }
    }
    @Test
    public void whenCreateTypeServiceThenReturn(){
        BusinessTypeDto dtoSource = new BusinessTypeDto(1, "yyyyy");
        BusinessTypeDto dtoTarget = businessTypeService.createBusinessType(dtoSource);
        assertEquals(dtoSource.getType(), dtoTarget.getType());
        assertEquals(dtoSource.getId(), dtoTarget.getId());
    }

}
