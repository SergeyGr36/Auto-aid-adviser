package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    //@Query("select b from Business b join fetch all properties where b.businessUser.id = :id ")
    List<Business> findAllByBusinessUser_Id(Long id);
}
