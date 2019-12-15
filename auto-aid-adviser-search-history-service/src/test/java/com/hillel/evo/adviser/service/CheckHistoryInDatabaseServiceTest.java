package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.HistoryPoint;
import com.hillel.evo.adviser.mapper.HistoryBusinessMapper;
import com.hillel.evo.adviser.mapper.SearchHistoryMapper;
import com.hillel.evo.adviser.mapper.SearchHistoryMapperImpl;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.service.impl.BusinessServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.hillel.evo.adviser.mapper.SearchHistoryMapperTest.historyPointDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SearchHistoryApplication.class})
@Sql(value = {"/create-user.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-history.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor
public class CheckHistoryInDatabaseServiceTest extends BaseTest {

    @Autowired
    CheckHistoryInDatabaseService checkHistoryInDatabaseService;
    @Autowired
    SearchHistoryService searchHistoryService;
    @Autowired
    private BusinessServiceImpl businessService;
    @Autowired
    private AdviserUserDetailRepository repository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    HistoryBusinessMapper historyBusinessMapper;
    @Autowired
    SearchHistoryMapper historyMapper;

    public static HistoryPointDto historyPointDto;
    private static HistoryPoint historyPoint;
    public static Long userId;
    public static Business business;
    public static BusinessShortDto businessShortDto;
    public static List<BusinessShortDto> businessShortDtoList;
    public static List<Business> businessList;

    @BeforeEach
    public void init() {
        userId = repository.findByEmail("bvg@mail.com").get().getId();
        businessList = businessRepository.findAllByBusinessUserId(userId);
        businessShortDtoList = historyBusinessMapper.toBusinessShortDtoList(businessList);
        businessShortDto = businessShortDtoList.get(0);

        business = new Business();
        business.setId(businessList.get(0).getId());
        business.setName(businessList.get(0).getName());
        business.setLocation(businessList.get(0).getLocation());
        business.setContact(businessList.get(0).getContact());
        historyPointDto = new HistoryPointDto(userId, businessShortDtoList, LocalDateTime.now());
        historyPoint = historyMapper.toEntity(historyPointDto);
    }

    @Test
    public void whenNumOfPointLessThanFiveSavePoint() {
        HistoryPointDto savedHistoryPointDto = checkHistoryInDatabaseService.checkHistory(historyPointDto);
        historyPointDto.setId(savedHistoryPointDto.getId());

        List<HistoryPointDto> points = searchHistoryService.getAllHistory(historyPointDto.getUserId());

        assertEquals(savedHistoryPointDto, historyPointDto);
        assertEquals(1, points.size());
    }

    @Test
    public void whenNumOfPointMoreThanFiveDeleteLatestPointAndSavePoint() {
        HistoryPointDto historyPointDto1 = historyPointDto;
        historyPointDto1.setSearchDate(LocalDateTime.now().minusSeconds(10L));
        HistoryPointDto historyPointDto2 = historyPointDto;
        HistoryPointDto historyPointDto3 = historyPointDto;
        HistoryPointDto historyPointDto4 = historyPointDto;
        HistoryPointDto historyPointDto5 = historyPointDto;
        HistoryPointDto historyPointDto6 = historyPointDto;

        checkHistoryInDatabaseService.checkHistory(historyPointDto1);
        checkHistoryInDatabaseService.checkHistory(historyPointDto2);
        checkHistoryInDatabaseService.checkHistory(historyPointDto3);
        checkHistoryInDatabaseService.checkHistory(historyPointDto4);
        checkHistoryInDatabaseService.checkHistory(historyPointDto5);
        checkHistoryInDatabaseService.checkHistory(historyPointDto6);


        assertFalse(searchHistoryService.getAllHistory(historyPointDto6.getUserId()).contains(historyPointDto1));
    }
}
