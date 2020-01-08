package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Feedback;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.FeedbackRepository;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-all.sql", "/create-user.sql", "/create-business.sql", "/create-feedback.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class FeedbackServiceImplTest {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private FeedbackServiceImpl feedbackService;

    private Business business;
    private SimpleUser simpleUser;

    @BeforeEach
    private void init() {
        business = businessRepository.findAllByName("user 1 STO 1").get(0);
        simpleUser = simpleUserRepository.findAll().get(0);
    }

    @Test
    public void findFeedbackByBusinessIdReturnPage() {
        //when
        Page<FeedbackDto> feedbackByBusiness = feedbackService.findFeedbackByBusiness(business.getId(),0, 10);
        //then
        assertEquals(4, feedbackByBusiness.getTotalElements());
    }

    @Test
    public void findFeedbackByIdReturnDto() {
        //give
        Feedback feedback = feedbackRepository.findAll().get(0);
        //when
        FeedbackDto feedbackDto = feedbackService.findFeedback(feedback.getId());
        //then
        assertEquals(feedback.getText(), feedbackDto.getText());
    }

    @Test
    public void findFeedbackByUserIdReturnDto() {
        //give
        Feedback feedback = feedbackRepository.findAll().get(0);
        Long userId = feedback.getSimpleUser().getId();
        //when
        Page<FeedbackDto> feedbackByUser = feedbackService.findFeedbackByUser(userId, 0, 10);
        //then
        assertEquals(4, feedbackByUser.getTotalElements());
    }

    @Test
    public void saveFeedbackReturnDto() {
        //give
        FeedbackDto feedbackDto = getNewFeedbackDto();
        //when
        FeedbackDto saveFeedback = feedbackService.saveFeedback(feedbackDto, business.getId(), simpleUser.getId());
        //then
        assertNotNull(saveFeedback.getId());
        assertEquals(feedbackDto.getText(), saveFeedback.getText());
        assertEquals(business.getName(), saveFeedback.getBusiness().getName());
        assertEquals(simpleUser.getFirstName(), saveFeedback.getSimpleUser().getFirstName());
        assertNotNull(saveFeedback.getCreateDate());
    }

    @Test
    public void saveFeedbackWithBadUserReturnException() {
        //give
        FeedbackDto feedbackDto = getNewFeedbackDto();
        //then
        assertThrows(RuntimeException.class, () -> feedbackService.saveFeedback(feedbackDto, business.getId(), 999L));
    }

    @Test
    public void saveFeedbackWithBadBusinessReturnException() {
        //give
        FeedbackDto feedbackDto = getNewFeedbackDto();
        //then
        assertThrows(RuntimeException.class, () -> feedbackService.saveFeedback(feedbackDto, 999L, simpleUser.getId()));
    }

    @Test
    public void updateFeedbackReturnDto() {
        //give
        String text = "new text";
        FeedbackDto feedbackDto = getFeedbackDto();
        feedbackDto.setText(text);
        //when
        FeedbackDto updateFeedback = feedbackService.updateFeedback(feedbackDto, business.getId(), simpleUser.getId());
        //then
        assertEquals(feedbackDto.getText(), updateFeedback.getText());
        assertEquals(business.getId(), updateFeedback.getBusiness().getId());
        assertEquals(simpleUser.getId(), updateFeedback.getSimpleUser().getId());
    }

    @Test
    public void updateFeedbackWithBadUserReturnException() {
        //give
        FeedbackDto feedbackDto = getNewFeedbackDto();
        //then
        assertThrows(RuntimeException.class, () -> feedbackService.updateFeedback(feedbackDto, business.getId(), 999L));
    }

    @Test
    public void updateFeedbackWithBadBusinessReturnException() {
        //give
        FeedbackDto feedbackDto = getNewFeedbackDto();
        //then
        assertThrows(RuntimeException.class, () -> feedbackService.updateFeedback(feedbackDto, 999L, simpleUser.getId()));
    }

    private FeedbackDto getNewFeedbackDto() {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setText("test text");
        feedbackDto.setRating(5);

        return feedbackDto;
    }

    private FeedbackDto getFeedbackDto() {
        Feedback feedback = feedbackRepository.findAll().get(0);

        FeedbackDto feedbackDto = new FeedbackDto(
                feedback.getId(),
                feedback.getText(),
                feedback.getRating(),
                new BusinessShortDto(),
                new SimpleUserDto(),
                feedback.getCreateDate()
        );

        return feedbackDto;
    }

}
