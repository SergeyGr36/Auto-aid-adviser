package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    @Query("select b from Business b where b.businessUser.id = :id")
    List<Business> findAllByBusinessUserId(Long id);

    @Query("select distinct b from Business b " +
            "join fetch b.serviceForBusinesses " +
            "left join fetch b.workTimes " +
            "where b.businessUser.id = :id")
    List<Business> findBusinessesFetchServicesByBusinessUser_Id(@Param("id") Long id);

    @Query("select b from Business b " +
            "join fetch b.serviceForBusinesses " +
            "left join fetch  b.workTimes " +
            "where b.id = :id and b.businessUser.id = :userId")
    Optional<Business> findByIdAndBusinessUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("select b from Business b " +
            "join fetch b.serviceForBusinesses " +
            "left join fetch b.workTimes " +
            "where b.id = :id")
    Optional<Business> findById(@Param("id") Long id);

    @Query("select b.images from Business b where b.id = :id")
    List<Image> findImagesByBusinessId(@Param("id") Long id);

    List<Business> findAllByName(String name);

    @Query("select img from Business b join b.images img " +
            "where b.id = :businessId and b.businessUser.id = :userId and img.id = :imageId")
    Optional<Image> findImageByBusinessUserIdAndBusinessIdAndImageId(@Param("userId") Long userId,
                                                                     @Param("businessId") Long businessId,
                                                                     @Param("imageId") Long imageId);
}
