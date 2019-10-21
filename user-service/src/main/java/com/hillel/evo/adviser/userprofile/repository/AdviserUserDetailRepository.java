package com.hillel.evo.adviser.userprofile.repository;

import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviserUserDetailRepository extends JpaRepository<AdviserUserDetails, Long> {
    AdviserUserDetails findByActivationCode(String code);

    Boolean existsByEmail(String email);
}
