package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.WorkTimeDto;
import com.hillel.evo.adviser.entity.WorkTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
public class WorkTimeMapperTest {

    @Autowired
    private WorkTimeMapper mapper;

    @Test
    public void dtoToEntity() {
        //given
        WorkTimeDto workTimeDto = getWorkTimeDto();
        //when
        WorkTime workTime = mapper.toEntity(workTimeDto);
        //then
        assertEquals(workTime.getDay().getValue(), workTimeDto.getDay());
        assertEquals(workTime.getFromTime(), workTimeDto.getFromTime());
        assertEquals(workTime.getToTime(), workTimeDto.getToTime());
    }

    @Test
    public void entityToDto() {
        //given
        WorkTime workTime = getWorkTime();
        //when
        WorkTimeDto dto = mapper.toDto(workTime);
        //then
        assertEquals(dto.getDay(), workTime.getDay().getValue());
        assertEquals(dto.getFromTime(), workTime.getFromTime());
        assertEquals(dto.getToTime(), workTime.getToTime());
    }

    @Test
    public void whenDtoEqNullReturnNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    public void whenEntityEqNullReturnNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void entitySetToDtoSet() {
        assertEquals(mapper.toListDto(getWorkTimeSet()).size(), 4);
    }

    @Test
    public void dtoSetToEntitySet() {
        assertEquals(mapper.toListEntity(getWorkTimeDtoSet()).size(), 4);
    }

    private WorkTimeDto getWorkTimeDto() {
       return new WorkTimeDto(1, LocalTime.of(8, 00), LocalTime.of(19, 00));
    }

    private WorkTime getWorkTime() {
        return new WorkTime(DayOfWeek.MONDAY, LocalTime.of(8, 00), LocalTime.of(19, 00));
    }

    private Set<WorkTimeDto> getWorkTimeDtoSet() {
        Set<WorkTimeDto> dtos = new HashSet<>();
        dtos.add(new WorkTimeDto(1, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        dtos.add(new WorkTimeDto(2, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        dtos.add(new WorkTimeDto(3, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        dtos.add(new WorkTimeDto(4, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        return dtos;
    }

    private Set<WorkTime> getWorkTimeSet() {
        Set<WorkTime> workTimes = new HashSet<>();
        workTimes.add(new WorkTime(DayOfWeek.MONDAY, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        workTimes.add(new WorkTime(DayOfWeek.TUESDAY, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        workTimes.add(new WorkTime(DayOfWeek.WEDNESDAY, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        workTimes.add(new WorkTime(DayOfWeek.THURSDAY, LocalTime.of(8, 00), LocalTime.of(19, 00)));
        return workTimes;
    }

}
