package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {
    Optional<BusinessType> findByName(String name);
}
