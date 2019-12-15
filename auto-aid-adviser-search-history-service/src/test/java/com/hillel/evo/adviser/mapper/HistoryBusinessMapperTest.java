package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Contact;
import com.hillel.evo.adviser.entity.Location;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SearchHistoryApplication.class})
@RequiredArgsConstructor
@Sql(value = {"/create-user.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class HistoryBusinessMapperTest {

    @Autowired
    private AdviserUserDetailRepository repository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private HistoryBusinessMapper historyBusinessMapper;
    @Autowired
    private HistoryBusinessMapperImpl historyBusinessMapperImpl;

    public static Long userId;
    public static Business business;
    public static BusinessShortDto businessShortDto;
    public static Business emptyBusiness;
    public static BusinessShortDto emptyBusinessShortDto;
    public static List<BusinessShortDto> businessShortDtoList;
    public static List<Business> businessList;

    @BeforeEach
    public void init() {
        userId = repository.findByEmail("bvg@mail.com").get().getId();
        businessList = businessRepository.findAllByBusinessUserId(userId);
        businessShortDtoList = historyBusinessMapper.toBusinessShortDtoList(businessList);

        business = new Business();
        business.setId(businessList.get(0).getId());
        business.setName(businessList.get(0).getName());
        business.setLocation(businessList.get(0).getLocation());
        business.setContact(businessList.get(0).getContact());

        businessShortDto = businessShortDtoList.get(0);
        emptyBusiness = new Business();
        emptyBusinessShortDto = new BusinessShortDto();
    }

    @Test
    public void whenUseToBusinessThanReturnBusiness() {
        Business testBusiness = historyBusinessMapper.toBusiness(businessShortDto);

        assertEquals(testBusiness, business);
    }

    @Test
    public void whenUseToBusinessShortDtoThanReturnBusinessShortDto() {
        BusinessShortDto testBusinessShortDto = historyBusinessMapper.toBusinessShortDto(business);

        assertEquals(testBusinessShortDto, businessShortDto);
    }

    @Test
    public void whenUseToBusinessListThanReturnBusinessList() {
        List<Business> testBusinessList = historyBusinessMapper.toBusinessList(businessShortDtoList);

        assertEquals(testBusinessList, businessList);
    }

    @Test
    public void whenUseToBusinessShortDtoListThanReturnBusinessShortDtoList() {
        List<BusinessShortDto> testBusinessShortDtoList = historyBusinessMapper.toBusinessShortDtoList(businessList);

        assertEquals(testBusinessShortDtoList, businessShortDtoList);
    }

    @Test
    public void whenOneOfObjectEqualsNullThanReturnNull() {
        business = null;
        businessShortDto = null;
        businessList = null;
        businessShortDtoList = null;
        LocationDto locationDto = null;
        ContactDto contactDto = null;
        Location location = null;
        Contact contact = null;

        assertNull(historyBusinessMapper.toBusinessShortDto(business));
        assertNull(historyBusinessMapper.toBusiness(businessShortDto));
        assertNull(historyBusinessMapper.toBusinessShortDtoList(businessList));
        assertNull(historyBusinessMapper.toBusinessList(businessShortDtoList));
        assertNull(historyBusinessMapperImpl.locationDtoToLocation(locationDto));
        assertNull(historyBusinessMapperImpl.locationToLocationDto(location));
        assertNull(historyBusinessMapperImpl.contactDtoToContact(contactDto));
        assertNull(historyBusinessMapperImpl.contactToContactDto(contact));
    }
}
