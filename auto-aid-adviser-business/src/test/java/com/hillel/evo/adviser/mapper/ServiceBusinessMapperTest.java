package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.dto.ServiceBusinessShortDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ServiceBusinessMapperTest {

    @Autowired
    ServiceBusinessMapperImpl mapper;

    @Test
    public void whenToDto_SetNull_ReturnNull() {
        Assertions.assertNull(mapper.toDto((ServiceBusiness) null));
    }

    @Test
    public void whenToDto_SetNullList_ReturnEmptyList() {
        Assertions.assertNull(mapper.toDto((List<ServiceBusiness>) null));
    }

    @Test
    public void whenToDto_SetEmptyList_ReturnEmptyList() {
        Assertions.assertTrue(mapper.toDto(new ArrayList<>()).isEmpty());
    }

    @Test
    public void whenToDto_SetList_ReturnListDto() {
        List<ServiceBusiness> list = Arrays.asList(new ServiceBusiness());
        Assertions.assertEquals(mapper.toDto(list).size(), list.size());
    }

    @Test
    public void whenToEntity_SetNull_ReturnNull() {
        Assertions.assertNull(mapper.toEntity((ServiceBusinessDto) null));
    }

    @Test
    public void whenToEntity_SetNullList_ReturnEmptyList() {
        Assertions.assertNull(mapper.toEntity((List<ServiceBusinessDto>) null));
    }

    @Test
    public void whenToEntity_SetEmptyList_ReturnEmptyList() {
        List<ServiceBusinessDto> list = new ArrayList<>();
        Assertions.assertTrue(mapper.toEntity(list).isEmpty());
    }

    @Test
    public void whenToEntity_SetList_ReturnListEntity() {
        List<ServiceBusinessDto> list = Arrays.asList(new ServiceBusinessDto());
        Assertions.assertEquals(mapper.toEntity(list).size(), list.size());
    }

    @Test
    public void whenToEntity_ReturnNull() {
        Assertions.assertNull(mapper.toEntity((ServiceBusinessShortDto) null));
    }
}
