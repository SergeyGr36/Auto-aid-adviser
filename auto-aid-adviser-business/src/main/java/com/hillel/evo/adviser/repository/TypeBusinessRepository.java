package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.TypeBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeBusinessRepository extends JpaRepository<TypeBusiness, Long> {
}
