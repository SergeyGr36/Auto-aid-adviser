package com.hillel.evo.adviser.userprofile.repository;

import com.hillel.evo.adviser.userprofile.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {

}
