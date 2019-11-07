package com.hillel.evo.adviser;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceBusinessShortDto;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.ServiceBusinessRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessServiceImplTest {

    @Autowired
    private BusinessServiceImpl businessService;

    @Autowired
    private AdviserUserDetailRepository repository;

    @Autowired
    private ServiceBusinessRepository serviceBusinessRepository;

    Long userId;

    @BeforeEach
    private void init() {
        userId = repository.findByEmail("bvg@mail.com").get().getId();
    }

    @Test
    public void whenCreateBusinessThenReturnDto() {
        //when
        BusinessDto saveDto = businessService.createBusiness(createTestDto());
        //then
        assertNotNull(saveDto);
        assertEquals(saveDto.getServiceBusinesses().size(), 2);
    }

    @Test
    public void whenCreateBusinessThenReturnThrow() {
        assertThrows(Exception.class, () -> businessService.createBusiness(new BusinessDto()));
    }

    @Test
    public void whenSendIdUserThenReturnListBusinessDto() {
        //when
        List<BusinessDto> list = businessService.findAllByUser(userId);
        //then
        assertEquals(list.size(), 2);
    }

    private BusinessDto createTestDto() {
        List<ServiceBusiness> list = serviceBusinessRepository.findAll();

        BusinessDto dto = new BusinessDto();
        dto.setName("some name");
        dto.setUserId(userId);

        LocationDto location = new LocationDto();
        location.setLatitude(99);
        location.setLongitude(99);
        location.setAddress("some address");

        ContactDto contact = new ContactDto();
        contact.setPhone("2345678");

        ServiceBusinessShortDto serviceBusiness1 = new ServiceBusinessShortDto();
        serviceBusiness1.setId(list.get(0).getId());

        ServiceBusinessShortDto serviceBusiness2 = new ServiceBusinessShortDto();
        serviceBusiness2.setId(list.get(1).getId());

        dto.setContact(contact);
        dto.setLocation(location);
        dto.setServiceBusinesses(Arrays.asList(serviceBusiness1, serviceBusiness2));

        return dto;
    }
}
