package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.mapper.SearchHistoryMapper;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SearchHistoryApplication.class})
@Sql(value = {"/create-user.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-history.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SearchHistoryServiceTest extends BaseTest {
    @Autowired
    SearchHistoryService searchHistoryService;
    @Autowired
    CheckHistoryInDatabaseService checkHistoryInDatabaseService;
    @Autowired
    private AdviserUserDetailRepository repository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    SearchHistoryMapper historyMapper;

    private static HistoryPointDto historyPointDto;

    @BeforeEach
    public void init() {
        Long userId = repository.findByEmail("bvg@mail.com").get().getId();
        List<Business> businessList = businessRepository.findAllByBusinessUserId(userId);
        List<BusinessShortDto> businessShortDtoList = businessMapper.toShortDtoList(businessList);
        historyPointDto = new HistoryPointDto(userId, businessShortDtoList, LocalDateTime.now());
    }

    @Test
    public void whenInsertHistoryPointThanReturnSavedDto() {
        HistoryPointDto testHistoryPointDto = historyPointDto;
        HistoryPointDto point = searchHistoryService.saveHistoryPoint(historyPointDto);
        testHistoryPointDto.setId(point.getId());

        assertEquals(point, historyPointDto);
    }

    @Test
    public void whenGetSearchDataThanReturnPointsList() {
        HistoryPointDto historyPointDto1 = new HistoryPointDto(1L,
                historyPointDto.getBusinessDto(), LocalDateTime.now().minusSeconds(10L));
        HistoryPointDto historyPointDto2 = new HistoryPointDto(historyPointDto.getUserId(),
                historyPointDto.getBusinessDto(), LocalDateTime.now().minusSeconds(5L));
        HistoryPointDto historyPointDto3 = new HistoryPointDto(historyPointDto.getUserId(),
                historyPointDto.getBusinessDto(), LocalDateTime.now());

        List<HistoryPointDto> points = new ArrayList<>();
        points.add(searchHistoryService.saveHistoryPoint(historyPointDto2));
        points.add(searchHistoryService.saveHistoryPoint(historyPointDto3));
        searchHistoryService.saveHistoryPoint(historyPointDto1);

        assertEquals(points, searchHistoryService.getAllHistory(historyPointDto2.getUserId()));
    }
}
