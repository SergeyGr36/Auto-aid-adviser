package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.entity.ServiceType;

import java.util.List;

public interface BusinessTypeTextSearch<T> {
    List<BusinessType> businessSearch(Class<T> clazz, String id, String name);

}
