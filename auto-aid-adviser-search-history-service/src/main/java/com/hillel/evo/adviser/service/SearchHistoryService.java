package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.HistoryPoint;
import com.hillel.evo.adviser.mapper.SearchHistoryMapper;
import com.hillel.evo.adviser.repository.SearchHistoryRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SearchHistoryService {
    private transient SearchHistoryRepo searchHistoryRepo;
    private transient SearchHistoryMapper searchHistoryMapper;

    SearchHistoryService(SearchHistoryRepo searchHistoryRepo, SearchHistoryMapper searchHistoryMapper) {
        this.searchHistoryRepo = searchHistoryRepo;
        this.searchHistoryMapper = searchHistoryMapper;
    }

    public List<HistoryPointDto> getAllHistory(Long userId) {
        List<HistoryPointDto> points = searchHistoryMapper.toDtoList(
                searchHistoryRepo.findAllByUserIdOrderBySearchDate(userId));
        return points;
    }

    @Transactional
    public HistoryPointDto saveHistoryPoint(HistoryPointDto historyPointDto) {
        HistoryPoint savedHistoryPoint = searchHistoryRepo.save(
                searchHistoryMapper.toEntity(historyPointDto));
        return searchHistoryMapper.toDto(savedHistoryPoint);
    }
}