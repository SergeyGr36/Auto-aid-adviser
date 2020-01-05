package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.entity.Feedback;
import com.hillel.evo.adviser.exception.CreateResourceException;
import com.hillel.evo.adviser.mapper.FeedbackMapper;
import com.hillel.evo.adviser.repository.FeedbackRepository;
import com.hillel.evo.adviser.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private transient final FeedbackRepository repository;
    private transient final FeedbackMapper mapper;


    @Override
    public FeedbackDto saveFeedback(FeedbackDto dto) {
        Feedback saveEntity = mapper.toEntity(dto);
        FeedbackDto savedFeedback = mapper.toDto(repository.save(saveEntity));
        return savedFeedback;
    }

    @Override
    public FeedbackDto updateFeedback(FeedbackDto dto) {
        Feedback updateEntity = mapper.toEntity(dto);
        FeedbackDto updatedFeedback = mapper.toDto(repository.save(updateEntity));
        return updatedFeedback;
    }

    @Override
    public FeedbackDto findFeedback(Long feedbackId) {
        Feedback updateFeedback = repository.findById(feedbackId).orElseThrow(() -> new CreateResourceException("Feedback not update"));
        return mapper.toDto(updateFeedback);
    }

    @Override
    public Page<FeedbackDto> findFeedbackByBusiness(Long businessId, Integer page, Integer size) {
        Page<Feedback> pageEntity = repository.findByBusinessId(businessId, PageRequest.of(page, size));
        return pageEntity.map(mapper::toDto);
    }

    @Override
    public Page<FeedbackDto> findFeedbackByUser(Long userId, Integer page, Integer size) {
        Page<Feedback> pageEntity = repository.findBySimpleUserId(userId, PageRequest.of(page, size));
        return pageEntity.map(mapper::toDto);
    }
}
