package com.hillel.evo.adviser.search.service;

import com.hillel.evo.adviser.search.dao.AidSearch;
import com.hillel.evo.adviser.search.dto.InputSearchDto;
import com.hillel.evo.adviser.search.entity.Aid;
import com.hillel.evo.adviser.search.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AidSearchService {

    private AidSearch aidSearch;

    @Autowired
    public void setAidSearch(AidSearch aidSearch) {
        this.aidSearch = aidSearch;
    }

    public List<Aid> Search(List<String> types, double distance, double curLat, double curLong) {

        InputSearchDto searchDto = DistanceUtil.get(types, distance, curLat, curLong);
        return aidSearch.search(searchDto);
    }
}
