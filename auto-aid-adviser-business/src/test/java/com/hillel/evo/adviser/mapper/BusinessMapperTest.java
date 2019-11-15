package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessMapperTest {

    @Autowired
    BusinessMapperImpl businessMapper;

    @Autowired
    BusinessUserRepository businessUserRepository;

    @Test
    public void whenToDto_SetNullReturnNull() {
        Assertions.assertNull(businessMapper.toDto(null));
    }

    @Test
    public void whenToEntity_SetDtoReturnEntity() {
        Assertions.assertNull(businessMapper.toEntity(null, null));
    }

    @Test
    public void whenListToDto_SetNullReturnNull() {
        Assertions.assertNull(businessMapper.listToDto(null));
    }

    @Test
    public void whenListToDto_SetEmptyList() {
        Assertions.assertEquals(businessMapper.listToDto(new ArrayList<Business>()).size(), 0);
    }

    @Test
    public void whenToEntity_SetEmptyDto_ReturnEmptyEntity() {
        //given
        BusinessDto dto = new BusinessDto();
        dto.setContact(null);
        dto.setLocation(null);
        dto.setServiceForBusinesses(new ArrayList<>());
        dto.getServiceForBusinesses().add(new ServiceForBusinessShortDto());
        //when
        Business business = businessMapper.toEntity(dto, null);
        //then
        Assertions.assertNull(business.getLocation());
        Assertions.assertNull(business.getContact());
    }

    @Test
    public void whenToEntity_SetFullDto_ReturnEntity() {
        //given
        BusinessUser user = businessUserRepository.findAll().get(0);
        BusinessDto dto = new BusinessDto();
        ContactDto contact = new ContactDto();
        contact.setPhone("123445");
        dto.setContact(contact);
        LocationDto location = new LocationDto();
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        location.setAddress("address");
        dto.setLocation(location);
        dto.setServiceForBusinesses(new ArrayList<>());
        dto.getServiceForBusinesses().add(new ServiceForBusinessShortDto());
        //when
        Business business = businessMapper.toEntity(dto, null);
        //then
        Assertions.assertEquals(business.getLocation().getAddress(), dto.getLocation().getAddress());
        Assertions.assertEquals(business.getContact().getPhone(), dto.getContact().getPhone());
        Assertions.assertEquals(business.getServiceForBusinesses().size(), dto.getServiceForBusinesses().size());
    }

    @Test
    public void whenToDto_EmptyEntity_ReturnEmptyDto() {
        //given
        Business business = new Business();
        //when
        BusinessDto dto = businessMapper.toDto(business);
        Assertions.assertNull(dto.getLocation());
        Assertions.assertNull(dto.getContact());
    }

}
