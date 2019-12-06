package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {

    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc " +
            "left join fetch uc.brand br " +
            "left join fetch uc.typeCar tc " +
            "left join fetch uc.motorType mt " +
            "join fetch uc.simpleUser su where su.id = :userId")
    List<UserCar> findAllBySimpleUserId(Long userId);

    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc " +
            "left join fetch uc.brand br " +
            "left join fetch uc.typeCar tc " +
            "left join fetch uc.motorType mt " +
            "join fetch uc.simpleUser su where uc = :userCar")
    UserCar findFetchUserCar(UserCar userCar);

    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc " +
            "left join fetch uc.brand br " +
            "left join fetch uc.typeCar tc " +
            "left join fetch uc.motorType mt " +
            "join fetch uc.simpleUser su " +
            "where uc.id = :carId and su.id = :userId")
    Optional<UserCar> findByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);
}
