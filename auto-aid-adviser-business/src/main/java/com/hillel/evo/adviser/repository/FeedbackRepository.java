package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findById(Long id);

    @Query("select fb from Feedback fb join fetch fb.business b join fetch fb.simpleUser su " +
            "where b.id=:businessId and su.id=:simpleUserId")
    Optional<Feedback> findByBusinessIdAndSimpleUserId(Long businessId, Long simpleUserId);

    @Query(value = "select fb from  Feedback fb join fetch fb.business b join fetch fb.simpleUser su where b.id=:businessId",
            countQuery = "select count(fb) from Feedback fb where fb.business.id =:businessId ")
    Page<Feedback> findByBusinessId(Long businessId, Pageable pageable);

    @Query(value = "select fb from Feedback fb join fetch fb.business b join fetch fb.simpleUser su where su.id=:simpleUserId order by fb.createDate desc ",
            countQuery = "select count(fb) from Feedback fb where fb.simpleUser.id =:simpleUserId")
    Page<Feedback> findBySimpleUserId(Long simpleUserId, Pageable pageable);
}
