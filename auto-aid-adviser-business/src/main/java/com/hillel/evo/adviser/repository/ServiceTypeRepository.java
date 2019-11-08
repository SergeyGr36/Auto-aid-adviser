package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
        @Query("select st from ServiceType st join fetch st.businessType b where b.id = :id")
        List<ServiceType> findAllByBusinessType_Id(Long id);

        @Query("select st from ServiceType st join fetch st.businessType where st.id = :id")
        Optional<ServiceType> findById_Fetch(Long id);

        @Query("select st from ServiceType st join fetch st.businessType where st.name = :name")
        Optional<ServiceType> findByName(String name);
}
