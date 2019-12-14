package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.entity.Location;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.service.SearchHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class EntitySearch<T> implements TextSearch<T>,  CustomSearch<T>, BusinessTypeTextSearch<T>, LocationTextSearch<T>, ServiceTypeTextSearch<T> {

    private transient SearchHelperService searchService;
private ServiceTypeTextSearch<ServiceType>serviceType;
private BusinessTypeTextSearch<BusinessType>businessType;
private LocationTextSearch<Location>locationTextSearch;


    @Autowired
    public void setSearchService(SearchHelperService searchService) {
        this.searchService = searchService;
    }
@Autowired
public void setServiceTypeTextSearch(ServiceTypeTextSearch<ServiceType> serviceType){
    this.serviceType = serviceType;
}
    @Autowired
    public void setBusinessTypeTextSearch(BusinessTypeTextSearch<BusinessType> businessType){
        this.businessType = businessType;
    }
    @Autowired
    public void setLocationTextSearch(LocationTextSearch<Location> locationTextSearch){
        this.locationTextSearch = locationTextSearch;
    }

    @Override
    public List<BusinessType> businessSearch(Class<T> clazz, String id, String name){
        var query = searchService.getBusinessTypeQuery(clazz,name, id);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }
    @Override
    public List<ServiceType> serviceSearch(Class<T> clazz, String id, String name){
        var query = searchService.getServiceTypeQuery(clazz,name, id);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }
    @Override
    public List<Location> searchLocation(Class<T> clazz, double radius, double longtitude, double latitude, String address){
        var query = searchService.getSpatialQuery(clazz,radius,longtitude, latitude,address);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }


    @Override
    public List<T> search(Class<T> clazz, String field, String param) {

        var query = searchService.getTextQuery(clazz, field, param);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> searchWildcard(Class<T> clazz, String field, String param) {
        var query = searchService.getTextWildcardQuery(clazz, field, param);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }

   /* @Override
    public List<T> search(Class<T> clazz, double radius, double latitude, double longitude) {
        var query = searchService.getSpatialQuery(clazz, radius, latitude, longitude);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }*/

    @Override
    public List<T> search(Class<T> clazz, QueryFactory... queries) {
        var junction = searchService.getQueryBuilder(clazz).bool();
        Arrays.asList(queries).forEach(q -> junction.must(q.get()));
        var query = junction.createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        return jpaQuery.getResultList();
    }
}
