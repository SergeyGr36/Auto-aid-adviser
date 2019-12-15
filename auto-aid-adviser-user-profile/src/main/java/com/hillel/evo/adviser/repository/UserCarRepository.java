package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.entity.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCarRepository extends JpaRepository<UserCar, Long> {

//    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc " +
//            "left join fetch uc.brand br " +
//            "left join fetch uc.typeCar tc " +
//            "left join fetch uc.motorType mt " +
//            "left join fetch uc.fuel_type ft " +
//            "join fetch uc.simpleUser su where su.id = :userId")
    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc where uc.simpleUser.id = :userId")
    List<UserCar> findAllBySimpleUserId(@Param("userId") Long userId);

//    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc " +
//            "left join fetch uc.brand br " +
//            "left join fetch uc.typeCar tc " +
//            "left join fetch uc.motorType mt " +
//            "left join fetch uc.fuel_type ft " +
//            "join fetch uc.simpleUser su where uc = :userCar")
//    UserCar findFetchUserCar(UserCar userCar);

//    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc " +
//            "left join fetch uc.brand br " +
//            "left join fetch uc.typeCar tc " +
//            "left join fetch uc.motorType mt " +
//            "left join fetch uc.fuel_type ft " +
//            "join fetch uc.simpleUser su " +
//            "where uc.id = :carId and su.id = :userId")
    @Query("select uc from com.hillel.evo.adviser.entity.UserCar uc where uc.simpleUser.id=:userId and uc.id=:carId")
    Optional<UserCar> findByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);

    @Query("select u.images from com.hillel.evo.adviser.entity.UserCar u where u.id = :id")
    List<Image> findImagesByUserCarId(@Param("id") Long id);

    @Query("select img from com.hillel.evo.adviser.entity.UserCar u join u.images img " +
            "where u.simpleUser.id = :userId and u.id = :userCarId and  img.id = :imageId")
    Optional<Image> findImageByUserIdAndUserCarIdAndImageId(@Param("userId") Long userId,
                                                                     @Param("userCarId") Long userCarId,
                                                                     @Param("imageId") Long imageId);

//    @Query("select email from com.hillel.evo.adviser.entity.AdviserUserDetails  where id = :userId")
//    String getEmailByUserId(@Param("userId") Long userId);
}
