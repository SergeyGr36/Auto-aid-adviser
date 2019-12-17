package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarIdentification;
import com.hillel.evo.adviser.entity.identification.CarModel;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarIdentificationRepository extends JpaRepository<CarIdentification, Long> {

    @Query("select distinct carmodel from CarModel carmodel " +
            "join fetch carmodel.parent carbrand " +
            "join fetch carbrand.parent typecar " +
            "where carmodel.name = :name")
    public CarModel findCarModelByName(@Param("name") String name);

    @Query("select distinct carbrand from CarBrand carbrand " +
            "join fetch carbrand.parent typecar " +
            "where carbrand.name = :name")
    public CarBrand findCarBrandByName(@Param("name") String name);

    @Query("select typecar from TypeCar typecar " +
            "where typecar.name = :name")
    public TypeCar findTypeCarByName(@Param("name") String name);
}
