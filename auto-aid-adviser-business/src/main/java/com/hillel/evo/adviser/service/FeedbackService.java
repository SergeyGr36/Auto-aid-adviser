package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.FeedbackDto;
import org.springframework.data.domain.Page;

public interface FeedbackService {
    FeedbackDto saveFeedback(FeedbackDto dto);
    FeedbackDto updateFeedback(FeedbackDto dto);
    FeedbackDto findFeedback(Long feedbackId);
    Page<FeedbackDto> findFeedbackByBusiness(Long businessId, Integer page, Integer size);
    Page<FeedbackDto> findFeedbackByUser(Long userId, Integer page, Integer size);
}
