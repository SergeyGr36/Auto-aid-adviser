package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.HistoryPoint;
import com.hillel.evo.adviser.mapper.SearchHistoryMapper;
import com.hillel.evo.adviser.repository.SearchHistoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CheckHistoryInDatabaseService {
    private transient final SearchHistoryRepo searchHistoryRepo;
    private transient final SearchHistoryService historyService;
    private transient final SearchHistoryMapper historyMapper;

    @Transactional
    public HistoryPointDto checkHistory(final HistoryPointDto historyPointDto) {
        List<HistoryPoint> allHistory = searchHistoryRepo.findAllByUserIdOrderBySearchDate(historyPointDto.getUserId());
        List<HistoryPointDto> historyPointDtoList = historyMapper.toDtoList(allHistory);
        final int MAX_CAPACITY = 5;
        if (historyPointDtoList.size() >= MAX_CAPACITY) {
            Long updateEntityId = historyPointDtoList.get(0).getId();
            HistoryPointDto update = new HistoryPointDto(updateEntityId, historyPointDto.getUserId(),
                    historyPointDto.getBusinessDto(), historyPointDto.getSearchDate());
            return historyService.saveHistoryPoint(update);
        } else {
            return historyService.saveHistoryPoint(historyPointDto);
        }
    }
}
