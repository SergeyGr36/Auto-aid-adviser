package com.hillel.evo.adviser;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessServiceImplTest {

    @Autowired
    private BusinessService businessService;

    @Test
    public void whenCreateBusinessThenReturnDto() {
        final BusinessDto dto = new BusinessDto();
            dto.setName("some name");
        final LocationDto location = new LocationDto();
            location.setLatitude(100);
            location.setLongitude(100);
            location.setAddress("some address");

        final ContactDto contact = new ContactDto();
            contact.setPhone("2345678");

            dto.setContact(contact);
            dto.setLocation(location);

        BusinessDto saveDto = businessService.createBusiness(dto);
        assertNotNull(saveDto);
    }
}
