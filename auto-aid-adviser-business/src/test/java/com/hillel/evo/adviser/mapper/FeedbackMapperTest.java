package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Feedback;
import com.hillel.evo.adviser.entity.SimpleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
public class FeedbackMapperTest {

    @Autowired
    private transient FeedbackMapperImpl feedbackMapper;

    @Test
    public void toEntityThenReturnEntity() {
        FeedbackDto feedbackDto = getFeedbackDto();
        //when
        Feedback feedback = feedbackMapper.toEntity(feedbackDto, new Business(), new SimpleUser());
        //then
        assertEquals(feedbackDto.getId(), feedback.getId());
        assertEquals(feedbackDto.getText(), feedback.getText());
    }

    @Test
    public void toEntityThenReturnNull() {
        Feedback feedback = feedbackMapper.toEntity(null, null, null);
        //then
        assertNull(feedback);
    }

    @Test
    public void toEntityNullBusinessAndUserThenReturnDto() {
        Feedback feedback = feedbackMapper.toEntity(getFeedbackDto(), null, null);
        //then
        assertNull(feedback.getBusiness());
        assertNull(feedback.getSimpleUser());
    }

    @Test
    public void toEntityDtoNullThenReturnDto() {
        Feedback feedback = feedbackMapper.toEntity(null, null, new SimpleUser());
        //then
        assertNull(feedback.getId());
        assertNull(feedback.getText());
    }

    @Test
    public void toDtoThenReturnDto() {
        Feedback feedback = getFeedback();
        //when
        FeedbackDto feedbackDto = feedbackMapper.toDto(feedback);
        //then
        assertEquals(feedback.getId(), feedbackDto.getId());
        assertEquals(feedback.getText(), feedbackDto.getText());
    }

    @Test
    public void toDtoThenReturnNull() {
        FeedbackDto feedbackDto = feedbackMapper.toDto(null);
        //then
        assertNull(feedbackDto);
    }

    @Test
    public void toListDtoThenReturnList() {
        List<Feedback> list = Arrays.asList(getFeedback());
        List<FeedbackDto> listDto = feedbackMapper.toListDto(list);
        //then
        assertEquals(list.size(), listDto.size());
    }

    @Test
    public void toListDtoThenReturnEmptyList() {
        List<Feedback> list = new ArrayList<>();
        List<FeedbackDto> listDto = feedbackMapper.toListDto(list);
        //then
        assertEquals(list.size(), listDto.size());
    }

    @Test
    public void toListDtoThenReturnNull() {
        List<FeedbackDto> listDto = feedbackMapper.toListDto(null);
        //then
        assertNull(listDto);
    }

    @Test
    public void updateEntityThenReturnEntity() {
        FeedbackDto feedbackDto = getFeedbackDto();
        Feedback feedback = feedbackMapper.updateEntity(feedbackDto, getFeedback());
        //then
        assertEquals(feedbackDto.getText(), feedback.getText());
    }

    @Test
    public void updateEntityThenReturnNull() {
        Feedback feedback = feedbackMapper.updateEntity(null, null);
        //then
        assertNull(feedback);
    }

    private Feedback getFeedback() {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setText("some text");
        feedback.setRating(5);

        return feedback;
    }

    private FeedbackDto getFeedbackDto() {
        FeedbackDto dto = new FeedbackDto(
                1L,
                "text dto",
                5,
                new BusinessShortDto(),
                new SimpleUserDto(),
                LocalDateTime.now());

        return dto;
    }
}
