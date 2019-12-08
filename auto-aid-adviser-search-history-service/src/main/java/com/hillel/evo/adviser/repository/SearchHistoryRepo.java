package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.HistoryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchHistoryRepo extends JpaRepository<HistoryPoint, Long> {

    List<HistoryPoint> findAllByUserIdOrderBySearchDate(Long userId);

}
