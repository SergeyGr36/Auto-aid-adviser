package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.ServiceBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceBusinessRepository extends JpaRepository<ServiceBusiness, Long> {
    Optional<ServiceBusiness> findByName(String name);

    @Query("select sb from ServiceBusiness sb join fetch sb.serviceType st where st.id = :id")
    List<ServiceBusiness> findAllByServiceType_Id(Long id);
}
