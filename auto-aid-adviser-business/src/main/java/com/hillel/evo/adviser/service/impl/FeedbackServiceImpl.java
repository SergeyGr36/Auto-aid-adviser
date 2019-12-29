package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.service.FeedbackService;
import org.springframework.data.domain.Page;

public class FeedbackServiceImpl implements FeedbackService {
    @Override
    public FeedbackDto saveFeedback(FeedbackDto dto) {
        return null;
    }

    @Override
    public FeedbackDto updateFeedback(FeedbackDto dto) {
        return null;
    }

    @Override
    public FeedbackDto findFeedback(Long feedbackId) {
        return null;
    }

    @Override
    public Page<FeedbackDto> findFeedbackByBusiness(Long businessId, Integer page, Integer size) {
        return null;
    }

    @Override
    public Page<FeedbackDto> findFeedbackByUser(Long userId, Integer page, Integer size) {
        return null;
    }
}
