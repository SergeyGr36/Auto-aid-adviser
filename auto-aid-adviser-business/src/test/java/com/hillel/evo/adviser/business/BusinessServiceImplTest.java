package com.hillel.evo.adviser.business;

import com.hillel.evo.adviser.business.dto.BusinessDto;
import com.hillel.evo.adviser.business.dto.LocationDto;
import com.hillel.evo.adviser.business.repository.BusinessRepository;
import com.hillel.evo.adviser.business.service.BusinessServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BusinessApplication.class)
//@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessServiceImplTest {

    @Autowired
    private BusinessServiceImpl businessService;

    @Test
    public void whenCreateBusinessThenReturnDto() {
        final BusinessDto dto = new BusinessDto();
        final LocationDto location = new LocationDto();
            location.setLatitude(100);
            location.setLongitude(100);
            location.setAddress("some address");

            dto.setLocation(location);

        businessService.createBusiness(dto);
    }
}
