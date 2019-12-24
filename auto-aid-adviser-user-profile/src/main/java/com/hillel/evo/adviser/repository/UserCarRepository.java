package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {

    @Query("select distinct uc from UserCar uc " +
            "join fetch uc.carModel cm " +
            "join fetch cm.typeCar " +
            "join fetch cm.carBrand " +
            "left join fetch uc.images " +
            "where uc.simpleUser.id = :userId")
    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false")})
    List<UserCar> findAllBySimpleUserId(@Param("userId") Long userId);

    @Query("select distinct uc from UserCar uc " +
            "join fetch uc.carModel cm " +
            "join fetch cm.typeCar " +
            "join fetch cm.carBrand " +
            "left join fetch uc.images " +
            "where uc.simpleUser.id = :userId and uc.id = :carId")
    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false")})
    Optional<UserCar> findByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);

    @Query("select distinct uc from UserCar uc " +
            "join fetch uc.carModel cm " +
            "join fetch cm.typeCar " +
            "join fetch cm.carBrand " +
            "left join fetch uc.images " +
            "where uc.id = :carId")
    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false")})
    Optional<UserCar> findByCarId(@Param("carId") Long carId);

    @Query("select u.images from UserCar u where u.id = :id")
    List<Image> findImagesByUserCarId(@Param("id") Long id);

/*
    @Query("select distinct car from UserCar car left join fetch car.images where car in :cars")
    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false")})
    List<UserCar> findImagesByUserCars(@Param("cars") List<UserCar> cars);
*/

    @Query("select img from UserCar u join u.images img " +
            "where u.simpleUser.id = :userId and u.id = :userCarId and  img.id = :imageId")
    Optional<Image> findImageByUserIdAndUserCarIdAndImageId(@Param("userId") Long userId,
                                                         @Param("userCarId") Long userCarId,
                                                         @Param("imageId") Long imageId);

    @Query("select uc.simpleUser from UserCar uc where uc.id = :id")
    Optional<SimpleUser> findSimpleUserByUserCardId(@Param("id") Long id);
}
