package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.HistoryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchHistoryRepo extends JpaRepository<HistoryPoint, Long> {

    @Query("select distinct hp from HistoryPoint hp join fetch hp.business b where hp.userId=:userId order by hp.searchDate, b.id")
    List<HistoryPoint> findAllByUserIdOrderBySearchDate(Long userId);

}
