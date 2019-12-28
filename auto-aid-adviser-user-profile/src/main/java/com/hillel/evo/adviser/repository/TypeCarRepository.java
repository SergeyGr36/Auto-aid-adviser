package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.TypeCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TypeCarRepository extends JpaRepository<TypeCar, Long> {

    Optional<TypeCar> findByName(String name);
    Optional<TypeCar> findById(Long id);

}