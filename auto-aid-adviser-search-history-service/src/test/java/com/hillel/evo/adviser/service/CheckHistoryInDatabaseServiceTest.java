package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.HistoryLocationDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static com.hillel.evo.adviser.mapper.SearchHistoryMapperTest.historyPointDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SearchHistoryApplication.class})
@Sql(statements = {"TRUNCATE TABLE history_point"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CheckHistoryInDatabaseServiceTest extends BaseTest {
    @Autowired
    CheckHistoryInDatabaseService checkHistoryInDatabaseService;
    @Autowired
    SearchHistoryService searchHistoryService;

    public static HistoryPointDto historyPointDtoInit;

    @BeforeEach
    public void init() {
        historyPointDtoInit = searchHistoryService.saveHistoryPoint(new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 2L));
    }

    @Test
    public void whenNumOfPointLessThanFiveSavePoint() {
        HistoryPointDto savedHistoryPointDto = checkHistoryInDatabaseService.checkHistory(new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 3L));

        HistoryPointDto checkHistoryPointDto = new HistoryPointDto(savedHistoryPointDto.getId(), 1L, savedHistoryPointDto.getSearchDate(),
                new HistoryLocationDto(100.5, 99.12), 3L);

        assertEquals(savedHistoryPointDto, checkHistoryPointDto);
        assertEquals(2, searchHistoryService.getAllHistory(historyPointDto.getUserId()).size());
    }

    @Test
    public void whenNumOfPointMoreThanFiveDeleteLatestPointAndSavePoint() {
        HistoryPointDto historyPointDto2 = new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 3L);
        HistoryPointDto historyPointDto3 = new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 4L);
        HistoryPointDto historyPointDto4 = new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 5L);
        HistoryPointDto historyPointDto5 = new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 6L);
        HistoryPointDto savePoint = new HistoryPointDto(1L, LocalDateTime.now(),
                new HistoryLocationDto(100.5, 99.12), 7L);
        HistoryPointDto savedDto = new HistoryPointDto(1L, 1L, historyPointDto.getSearchDate(),
                new HistoryLocationDto(100.5, 99.12), 2L);

        checkHistoryInDatabaseService.checkHistory(historyPointDto2);
        checkHistoryInDatabaseService.checkHistory(historyPointDto3);
        checkHistoryInDatabaseService.checkHistory(historyPointDto4);
        checkHistoryInDatabaseService.checkHistory(historyPointDto5);
        checkHistoryInDatabaseService.checkHistory(savePoint);

        assertFalse(searchHistoryService.getAllHistory(savePoint.getUserId()).contains(savedDto));
    }
}
