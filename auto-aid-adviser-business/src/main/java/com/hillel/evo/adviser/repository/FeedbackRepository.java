package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select fb1 from Feedback fb1 " +
            "join fetch fb1.business b " +
            "join fetch fb1.simpleUser su " +
            "where fb1.id = :id")
    Optional<Feedback> findById(Long id);

    @Query("select fb2 from Feedback fb2 " +
            "join fetch fb2.business b " +
            "join fetch fb2.simpleUser su " +
            "where fb2.id = :feedbackId and b.id = :businessId and su.id = :simpleUserId")
    Optional<Feedback> findByFeedbackIdAndAndBusinessIdAndSimpleUserId(
            @Param("feedbackId") Long feedbackId,
            @Param("businessId") Long businessId,
            @Param("simpleUserId") Long simpleUserId);

    @Query(value = "select fb3 from Feedback fb3 " +
            "join fetch fb3.business b " +
            "join fetch fb3.simpleUser su " +
            "where b.id = :businessId and su.id = :simpleUserId " +
            "order by fb3.createDate desc",
            countQuery = "select count(fb) from Feedback fb " +
                    "where fb.business.id = :businessId and fb.simpleUser.id = :simpleUserId")
    Page<Feedback> findByBusinessIdAndSimpleUserId(@Param("businessId") Long businessId,
                                                   @Param("simpleUserId") Long simpleUserId,
                                                   Pageable pageable);

    @Query(value = "select fb4 from Feedback fb4 " +
            "join fetch fb4.business b " +
            "join fetch fb4.simpleUser su " +
            "where b.id = :businessId " +
            "order by fb4.createDate desc",
            countQuery = "select count(fb) from Feedback fb where fb.business.id = :businessId ")
    Page<Feedback> findByBusinessId(@Param("businessId") Long businessId, Pageable pageable);

    @Query(value = "select fb5 from Feedback fb5 " +
            "join fetch fb5.business b " +
            "join fetch fb5.simpleUser su " +
            "where su.id = :simpleUserId " +
            "order by fb5.createDate desc ",
            countQuery = "select count(fb) from Feedback fb where fb.simpleUser.id = :simpleUserId")
    Page<Feedback> findBySimpleUserId(@Param("simpleUserId") Long simpleUserId, Pageable pageable);
}
