package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchHistoryApplication;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.HistoryPoint;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SearchHistoryApplication.class})
@RequiredArgsConstructor
@Sql(value = {"/create-user.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SearchHistoryMapperTest extends BaseTest {

    @Autowired
    private SearchHistoryMapperImpl historyPointMapper;
    @Autowired
    private AdviserUserDetailRepository repository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private HistoryBusinessMapper historyBusinessMapper;

    public static List<BusinessShortDto> businessDtoList;
    public static HistoryPointDto historyPointDto;
    public static HistoryPoint historyPoint;
    public static Long userId;

    @BeforeEach
    public void init() {
        userId = repository.findByEmail("bvg@mail.com").get().getId();
        businessDtoList = historyBusinessMapper.toBusinessShortDtoList(businessRepository.findAllByBusinessUserId(userId));
        historyPointDto = new HistoryPointDto(2L, 1L, businessDtoList, LocalDateTime.now());
        historyPoint = historyPointMapper.toEntity(historyPointDto);
    }

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
        assertNull(emptyHistoryPointDto.getBusinessDto());
        assertNull(emptyHistoryPointDto.getSearchDate());
    }

    @Test
    public void whenUseEmptyDtoThanReturnEmptyEntity() {
        HistoryPointDto emptyHistoryPointDto = new HistoryPointDto();
        HistoryPoint emptyHistoryPoint = historyPointMapper.toEntity(emptyHistoryPointDto);

        assertNull(emptyHistoryPoint.getId());
        assertNull(emptyHistoryPoint.getUserId());
        assertNull(emptyHistoryPoint.getBusiness());
        assertNull(emptyHistoryPoint.getSearchDate());

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
        List<HistoryPoint> list = null;
        assertNull(historyPointMapper.toDtoList(list));
    }
}
