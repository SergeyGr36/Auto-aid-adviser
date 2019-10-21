package com.hillel.evo.advisor.business.repository;

import com.hillel.evo.advisor.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
