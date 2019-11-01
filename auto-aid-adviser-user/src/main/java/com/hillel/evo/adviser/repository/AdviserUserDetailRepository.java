package com.hillel.evo.adviser.repository;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdviserUserDetailRepository extends JpaRepository<AdviserUserDetails, Long> {
    Optional<AdviserUserDetails> findByActivationCode(String code);

    Boolean existsByEmail(String email);

    Optional<AdviserUserDetails> findByEmail(String email);
}
