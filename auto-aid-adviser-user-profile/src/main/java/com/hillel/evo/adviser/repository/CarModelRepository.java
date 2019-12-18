package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    @Query("select distinct carmodel from CarModel carmodel " +
            "join fetch carmodel.carBrand carbrand " +
            "join fetch carmodel.typeCar typecar " +
            "where carmodel.name = :name")
    Optional<CarModel> findByName(@Param("name") String name);

    @Query("select distinct carmodel from CarModel carmodel " +
            "join fetch carmodel.carBrand carbrand " +
            "join fetch carmodel.typeCar typecar " +
            "where carmodel.id = :id")
    Optional<CarModel> findById(@Param("id") Long id);

    @Query("select distinct carmodel from CarModel carmodel " +
            "join fetch carmodel.carBrand carbrand " +
            "join fetch carmodel.typeCar typecar " +
            "where typecar.name = :typeName and carbrand.name = :brandName")
    List<CarModel> findByTypeAndBrand(@Param("typeName") String typeName, @Param("brandName") String brandName);

    @Query("select distinct carmodel from CarModel carmodel " +
            "join fetch carmodel.carBrand carbrand " +
            "join fetch carmodel.typeCar typecar " +
            "where typecar.id = :typeId and carbrand.id = :brandId")
    List<CarModel> findByTypeAndBrand(@Param("typeId") Long typeId, @Param("brandId") Long brandId);

}
