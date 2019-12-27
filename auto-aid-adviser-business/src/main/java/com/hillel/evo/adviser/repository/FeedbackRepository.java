package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
