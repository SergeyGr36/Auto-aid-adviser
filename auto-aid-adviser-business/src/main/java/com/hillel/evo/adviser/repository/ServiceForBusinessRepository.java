package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.ServiceForBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceForBusinessRepository extends JpaRepository<ServiceForBusiness, Long> {
    Optional<ServiceForBusiness> findByName(String name);

    @Query("select distinct sb from ServiceForBusiness sb join fetch sb.serviceType st where st.id = :id")
    List<ServiceForBusiness> findAllByServiceTypeId(@Param("id") Long id);

/*
    @Query("select distinct sb from ServiceForBusiness sb join fetch sb.serviceType st join fetch st.businessType")
    List<ServiceForBusiness> findAll();
*/

    @Transactional
    @Query(value = "select sb from ServiceForBusiness sb join fetch sb.serviceType st join fetch st.businessType",
            countQuery = "select count(sb) from ServiceForBusiness sb")
    Page<ServiceForBusiness> byPages(Pageable pageable);

    @Query("select b.serviceForBusinesses from Business b where b.id = :businessId and b.businessUser.id = :userId")
    List<ServiceForBusiness> findServicesByBusiness_IdAndBusinessUser_Id(Long businessId, Long userId);
}
