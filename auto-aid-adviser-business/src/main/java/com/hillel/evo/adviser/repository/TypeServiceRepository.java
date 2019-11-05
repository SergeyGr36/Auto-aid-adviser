package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeServiceRepository extends JpaRepository<ServiceType, Long> {
        }
