package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {
    @Query("select  '*' from UserCar c"+
            " where  c.id = :userCarId and c.simpleUser =:id")
    Optional<UserCar> findBySimpleUserIdAndUserCarId(@Param("id") Long userId, @Param("userCarId") Long userCarId);
}
