package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    @Query("select b from Business b where b.businessUser.id = :id")
    List<Business> findAllByBusinessUserId(@Param("id") Long id);

    @Query("select distinct b from Business b join fetch b.serviceForBusinesses where b.businessUser.id = :id")
    List<Business> findBusinessesUserId(@Param("id") Long id);

    @Query("select b from Business b join fetch b.serviceForBusinesses where b.id = :id and b.businessUser.id = :userId")
    Optional<Business> findByIdAndBusinessUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("select b from Business b join fetch b.serviceForBusinesses where b.id = :id")
    Optional<Business> findByBusinessId(@Param("id") Long id);

}
