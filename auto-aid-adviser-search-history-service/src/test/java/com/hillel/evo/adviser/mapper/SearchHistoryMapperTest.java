package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.HistoryLocationDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.HistoryLocation;
import com.hillel.evo.adviser.entity.HistoryPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SearchHistoryApplication.class})
public class SearchHistoryMapperTest extends BaseTest {

    @Autowired
    SearchHistoryMapperImpl historyPointMapper;

    public static HistoryPointDto historyPointDto = new HistoryPointDto(2L, 1L, LocalDateTime.now(), new HistoryLocationDto(100.5, 99.12), 2L);
    public static HistoryPoint historyPoint = new HistoryPoint(2L, 1L, historyPointDto.getSearchDate(), new HistoryLocation(100.5, 99.12), 2L);


    @Test
    public void whenUseToDtoThanReturnDto() {
        assertEquals(historyPointDto, historyPointMapper.toDto(historyPoint));
    }

    @Test
    public void whenUseToEntityThanReturnEntity() {
        assertEquals(historyPoint, historyPointMapper.toEntity(historyPointDto));
    }

    @Test
    public void whenUseToDtoListThanReturnListOfDto() {
        List<HistoryPoint> pointList = new ArrayList<>();
        pointList.add(historyPoint);
        pointList.add(historyPoint);

        List<HistoryPointDto> pointDtoList = new ArrayList<>();
        pointDtoList.add(historyPointDto);
        pointDtoList.add(historyPointDto);

        assertEquals(pointDtoList, historyPointMapper.toDtoList(pointList));
    }

    @Test
    public void whenUseEmptyEntityThanReturnEmptyDto() {
        HistoryPoint emptyHistoryPoint = new HistoryPoint();
        HistoryPointDto emptyHistoryPointDto = historyPointMapper.toDto(emptyHistoryPoint);

        assertNull(emptyHistoryPointDto.getId());
        assertNull(emptyHistoryPointDto.getUserId());
        assertNull(emptyHistoryPointDto.getLocation());
        assertNull(emptyHistoryPointDto.getSearchDate());
        assertNull(emptyHistoryPointDto.getServiceId());
    }

    @Test
    public void whenUseEmptyDtoThanReturnEmptyEntity() {
        HistoryPointDto emptyHistoryPointDto = new HistoryPointDto();
        HistoryPoint emptyHistoryPoint = historyPointMapper.toEntity(emptyHistoryPointDto);

        assertNull(emptyHistoryPoint.getId());
        assertNull(emptyHistoryPoint.getUserId());
        assertNull(emptyHistoryPoint.getLocation());
        assertNull(emptyHistoryPoint.getSearchDate());
        assertNull(emptyHistoryPoint.getServiceId());
    }

    @Test
    public void whenUseEmptyEntityListThenReturnEmptyList() {
        assertEquals(historyPointMapper.toDtoList(new ArrayList<HistoryPoint>()).size(), 0);
    }

    @Test
    public void whenHistoryPointDtoNullThanReturnNull() {
        HistoryPointDto historyPointDto = null;
        assertNull(historyPointMapper.toEntity(historyPointDto));
    }

    @Test
    public void whenHistoryPointNullThanReturnNull() {
        HistoryPoint historyPoint = null;
        assertNull(historyPointMapper.toDto(historyPoint));
    }

    @Test
    public void whenHistoryPointListNullThanReturnNull() {
        List<HistoryPoint> list=null;
        assertNull(historyPointMapper.toDtoList(list));
    }
}
