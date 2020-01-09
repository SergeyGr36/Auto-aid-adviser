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

    @Query("select b1 from Business b1 " +
            "join fetch b1.serviceForBusinesses " +
            "left join fetch b1.workTimes " +
            "where b1.id = :id and b1.businessUser.id = :userId")
    Optional<Business> findByIdAndBusinessUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("select b2 from Business b2 " +
            "join fetch b2.serviceForBusinesses " +
            "left join fetch b2.workTimes " +
            "where b2.id = :id")
    Optional<Business> findById(@Param("id") Long id);

    @Query("select distinct b3 from Business b3 " +
            "join fetch b3.serviceForBusinesses " +
            "left join fetch b3.workTimes " +
            "where b3 in :businesses")
    List<Business> findByBusiness(@Param("businesses") Iterable<Business> businesses);

    @Query("select b.images from Business b where b.id = :id")
    List<Image> findImagesByBusinessId(@Param("id") Long id);

    List<Business> findAllByName(String name);

    @Query("select img from Business b join b.images img " +
            "where b.id = :businessId and b.businessUser.id = :userId and img.id = :imageId")
    Optional<Image> findImageByBusinessUserIdAndBusinessIdAndImageId(@Param("userId") Long userId,
                                                                     @Param("businessId") Long businessId,
                                                                     @Param("imageId") Long imageId);
}
