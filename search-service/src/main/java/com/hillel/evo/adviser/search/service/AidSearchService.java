//package com.hillel.evo.adviser.search.service;
//
//import com.hillel.evo.adviser.search.dao.AidSearch;
//import com.hillel.evo.adviser.search.entity.Aid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AidSearchService {
//
//    private transient AidSearch aidSearch;
//
//    @Autowired
//    public void setAidSearch(AidSearch aidSearch) {
//        this.aidSearch = aidSearch;
//    }
//
//    public List<Aid> search(String type, double distance, double curLat, double curLong) {
//
//        return aidSearch.search(type, distance, curLat, curLong);
//    }
//}
