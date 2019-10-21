package com.hillel.evo.adviser.userprofile.repository;

import com.hillel.evo.adviser.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {

}
