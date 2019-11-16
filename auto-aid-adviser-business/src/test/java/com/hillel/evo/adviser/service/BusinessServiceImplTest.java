package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.impl.BusinessServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BusinessServiceImplTest {

    @Autowired
    private BusinessServiceImpl businessService;

    @Autowired
    private AdviserUserDetailRepository repository;

    @Autowired
    private ServiceForBusinessRepository serviceForBusinessRepository;

    Long userId;

    @BeforeEach
    private void init() {
        userId = repository.findByEmail("bvg@mail.com").get().getId();
    }

    @Test
    public void whenCreateBusinessThenReturnDto() {
        //when
        BusinessDto saveDto = businessService.createBusiness(createTestDto(), userId);
        //then
        assertNotNull(saveDto);
        assertEquals(saveDto.getServiceForBusinesses().size(), 2);
    }

    @Test
    public void whenCreateBusinessThenReturnThrow() {
        assertThrows(Exception.class, () -> businessService.createBusiness(new BusinessDto(), userId));
    }

    @Test
    public void whenFindByUserIdThenReturnListBusinessDto() {
        //when
        List<BusinessDto> list = businessService.findAllByUser(userId);
        //then
        assertEquals(list.size(), 2);
    }

    @Test
    public void whenFindByBusinessIdThenReturnBusinessDto() {
        //given
        BusinessDto dto = businessService.findAllByUser(userId).get(0);
        //when
        BusinessDto findBusinessDto = businessService.findBusinessById(dto.getId(), userId);
        //then
        assertEquals(findBusinessDto.getId(), dto.getId());
        assertEquals(findBusinessDto.getName(), dto.getName());
        assertEquals(findBusinessDto.getServiceForBusinesses().size(), dto.getServiceForBusinesses().size());
    }

    @Test
    public void whenFindByBusinessIdThenReturnException() {
        assertThrows(ResourceNotFoundException.class, () -> businessService.findBusinessById(99L, userId));
    }

    @Test
    public void whenUpdateBusinessThenReturnBusinessDto() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        sourceDto.setName("updating name");
        sourceDto.getServiceForBusinesses().remove(0);
        //when
        BusinessDto updateDto = businessService.updateBusiness(sourceDto, userId);
        //then
        assertEquals(updateDto.getId(), sourceDto.getId());
        assertEquals(updateDto.getName(), sourceDto.getName());
        assertEquals(updateDto.getServiceForBusinesses().size(), sourceDto.getServiceForBusinesses().size());
    }

    @Test
    public void whenDeleteBusinessThenNotThrow() {
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        assertDoesNotThrow(() -> businessService.deleteBusiness(sourceDto.getId(), userId));
    }

    @Test
    public void whenDeleteBusinessThenReturnException() {
        assertThrows(Exception.class, () -> businessService.deleteBusiness(99L, userId));
    }

    private BusinessDto createTestDto() {
        List<ServiceForBusiness> list = serviceForBusinessRepository.findAll();

        BusinessDto dto = new BusinessDto();
        dto.setName("some name");

        LocationDto location = new LocationDto();
        location.setLatitude(99);
        location.setLongitude(99);
        location.setAddress("some address");

        ContactDto contact = new ContactDto();
        contact.setPhone("2345678");

        ServiceForBusinessShortDto serviceBusiness1 = new ServiceForBusinessShortDto();
        serviceBusiness1.setId(list.get(0).getId());

        ServiceForBusinessShortDto serviceBusiness2 = new ServiceForBusinessShortDto();
        serviceBusiness2.setId(list.get(1).getId());

        dto.setContact(contact);
        dto.setLocation(location);
        dto.setServiceForBusinesses(Arrays.asList(serviceBusiness1, serviceBusiness2));

        return dto;
    }
}
