package com.hillel.evo.adviser.userprofile.repository;

import com.hillel.evo.adviser.userprofile.entity.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {

}
