package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.FeedbackDto;
import org.springframework.data.domain.Page;

public interface FeedbackService {
    FeedbackDto saveFeedback(FeedbackDto dto, Long businessId, Long simpleUserId);
    FeedbackDto updateFeedback(FeedbackDto dto, Long businessId, Long simpleUserId);
    FeedbackDto findFeedback(Long feedbackId);
    Page<FeedbackDto> findFeedbackByBusiness(Long businessId, Integer page, Integer size);
    Page<FeedbackDto> findFeedbackByUser(Long userId, Integer page, Integer size);
}
