package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {

    Optional<CarBrand> findByName(String name);
    Optional<CarBrand> findById(Long id);
}
