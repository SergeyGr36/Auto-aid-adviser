package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {

}
