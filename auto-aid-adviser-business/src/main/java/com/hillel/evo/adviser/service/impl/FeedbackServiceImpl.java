package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Feedback;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.exception.CreateResourceException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.FeedbackMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.FeedbackRepository;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private transient final FeedbackRepository feedbackRepository;
    private transient final BusinessRepository businessRepository;
    private transient final SimpleUserRepository simpleUserRepository;
    private transient final FeedbackMapper mapper;


    @Override
    public FeedbackDto saveFeedback(@NotNull FeedbackDto dto, @NotNull Long businessId, @NotNull Long simpleUserId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found business"));
        SimpleUser simpleUser = simpleUserRepository.findById(simpleUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user"));
        Feedback saveEntity = mapper.toEntity(dto, business, simpleUser);
        FeedbackDto savedFeedback = mapper.toDto(feedbackRepository.save(saveEntity));
        return savedFeedback;
    }

    @Override
    public FeedbackDto updateFeedback(FeedbackDto dto, @NotNull Long businessId, @NotNull Long simpleUserId) {
        Feedback feedback = feedbackRepository.findByFeedbackIdAndAndBusinessIdAndSimpleUserId(
                dto.getId(), businessId, simpleUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found feedback for update"));
        Feedback updateEntity = mapper.updateEntity(dto, feedback);
        feedbackRepository.save(updateEntity);
        FeedbackDto updatedFeedback = mapper.toDto(updateEntity);
        return updatedFeedback;
    }

    @Override
    public FeedbackDto findFeedback(Long feedbackId) {
        Feedback updateFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CreateResourceException("Feedback not update"));
        return mapper.toDto(updateFeedback);
    }

    @Override
    public Page<FeedbackDto> findFeedbackByBusiness(Long businessId, Integer page, Integer size) {
        Page<Feedback> pageEntity = feedbackRepository.findByBusinessId(businessId, PageRequest.of(page, size));
        return pageEntity.map(mapper::toDto);
    }

    @Override
    public Page<FeedbackDto> findFeedbackByUser(Long userId, Integer page, Integer size) {
        Page<Feedback> pageEntity = feedbackRepository.findBySimpleUserId(userId, PageRequest.of(page, size));
        return pageEntity.map(mapper::toDto);
    }
}
