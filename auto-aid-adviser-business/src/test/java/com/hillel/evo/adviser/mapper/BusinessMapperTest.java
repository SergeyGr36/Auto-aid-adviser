package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.*;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-all.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessMapperTest {

    @Autowired
    BusinessMapper businessMapper;

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
    public void whenToShortDto_SetNullReturnNull() {
        Assertions.assertNull(businessMapper.toShortDto(null));
    }

    @Test
    public void whenToEntityShort_SetNullReturnNull() {
        Assertions.assertNull(businessMapper.toEntityShort(null));
    }

    @Test
    public void whenToShortDtoList_SetNullReturnNull() {
        Assertions.assertNull(businessMapper.toShortDtoList(null));
    }

    @Test
    public void whenToShortDtoList_SetEmptyList() {
        Assertions.assertEquals(businessMapper.toShortDtoList(new ArrayList<>()).size(), 0);
    }

    @Test
    public void whenListToDto_SetEmptyList() {
        Assertions.assertEquals(businessMapper.listToDto(new ArrayList<Business>()).size(), 0);
    }

    @Test
    public void whenToEntityShort_SetEmptyDto_ReturnEmptyEntity() {
        //given
        BusinessShortDto dto = new BusinessShortDto();
        dto.setContact(null);
        dto.setLocation(null);
        //when
        Business business = businessMapper.toEntityShort(dto);
        //then
        Assertions.assertNull(business.getLocation());
        Assertions.assertNull(business.getContact());
    }

    @Test
    public void whenToShortDtoList_SetEmptyEntity_ReturnEmptyShortDtoList() {
        //given
        Business business = new Business();
        business.setContact(null);
        business.setLocation(null);
        business.setServiceForBusinesses(new HashSet<>());
        business.getServiceForBusinesses().add(new ServiceForBusiness());
        List<Business> businessList = new ArrayList<>();
        businessList.add(business);
        //when
        List<BusinessShortDto> shortDtos = businessMapper.toShortDtoList(businessList);
        //then
        Assertions.assertNull(shortDtos.get(0).getLocation());
        Assertions.assertNull(shortDtos.get(0).getContact());
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
        LocationDto location = new LocationDto(50.0, 50.0, "some address");
        contact.setPhone("123445");
        dto.setContact(contact);
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

    @Test
    public void whenToFullDto_ReturnDto() {
        //given
        Business business = new Business();
        //when
        BusinessFullDto dto = businessMapper.toFullDto(business);
        Assertions.assertNotNull(dto);
    }

    @Test
    public void whenToFullDto_ReturnNull() {
        //when
        BusinessFullDto dto = businessMapper.toFullDto(null);
        Assertions.assertNull(dto);
    }

    @Test
    public void whenEmptyLocationTest() {
        //given
        BusinessDto dto = new BusinessDto();
        LocationDto location = new LocationDto();
        dto.setLocation(location);
        //when
        Business business = businessMapper.toEntity(dto, null);
        //then
        Assertions.assertNull(business.getLocation().getLatitude());
        Assertions.assertNull(business.getLocation().getLongitude());
    }

}
