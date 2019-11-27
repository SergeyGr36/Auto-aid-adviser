package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.CarIdentification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarIdentificationRepo extends JpaRepository<CarIdentification, Long> {
}
