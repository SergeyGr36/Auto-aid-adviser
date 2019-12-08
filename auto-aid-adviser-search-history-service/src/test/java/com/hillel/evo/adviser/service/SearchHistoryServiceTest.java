package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.HistoryLocationDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
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
@Sql(statements = {"TRUNCATE TABLE history_point"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SearchHistoryServiceTest extends BaseTest {
    @Autowired
    SearchHistoryService searchHistoryService;
    @Test
    public void whenInsertHistoryPointThanReturnSavedDto(){
        HistoryPointDto historyPointDto=new HistoryPointDto(1L, LocalDateTime.now(), new HistoryLocationDto(100.5, 99.12), 2L);
        HistoryPointDto point= searchHistoryService.saveHistoryPoint(historyPointDto);
        historyPointDto.setId(point.getId());

        assertEquals(point, historyPointDto);
    }

    @Test
    public void whenGetSearchDataThanReturnPointsList(){
        HistoryPointDto historyPointDto1=new HistoryPointDto(1L,LocalDateTime.now(), new HistoryLocationDto(100.5, 99.12), 2L);
        HistoryPointDto historyPointDto2=new HistoryPointDto(2L, LocalDateTime.now(), new HistoryLocationDto(11.5, 66.12), 5L);
        HistoryPointDto historyPointDto3=new HistoryPointDto(2L, LocalDateTime.now(), new HistoryLocationDto(111.5, 166.12), 4L);

        List<HistoryPointDto> points= new ArrayList<>();
        points.add(searchHistoryService.saveHistoryPoint(historyPointDto2));
        points.add(searchHistoryService.saveHistoryPoint(historyPointDto3));
        searchHistoryService.saveHistoryPoint(historyPointDto1);

        assertEquals(points, searchHistoryService.getAllHistory(historyPointDto2.getUserId()));
    }
}
