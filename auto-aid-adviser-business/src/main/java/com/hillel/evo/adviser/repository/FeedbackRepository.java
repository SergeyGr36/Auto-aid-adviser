package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Override
    Optional<Feedback> findById(Long id);
    Optional<Feedback> findByBusinessIdAndSimpleUserId(Long businessId, Long simpleUserId);
    Page<Feedback> findByBusinessId(Long businessId, Pageable pageable);
    Page<Feedback> findBySimpleUserId(Long simpleUserId, Pageable pageable);
}
