package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.identification.CarIdentification;
import com.hillel.evo.adviser.entity.identification.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarIdentificationRepo extends JpaRepository<CarIdentification, Long> {

    public CarModel findByName(String name);
}
