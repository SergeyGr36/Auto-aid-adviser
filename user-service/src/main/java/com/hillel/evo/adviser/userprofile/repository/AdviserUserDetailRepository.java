package com.hillel.evo.adviser.userprofile.repository;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdviserUserDetailRepository extends JpaRepository<AdviserUserDetails, Long> {
    AdviserUserDetails findByActivationCode(String code);

    Boolean existsByMail(String email);
}
