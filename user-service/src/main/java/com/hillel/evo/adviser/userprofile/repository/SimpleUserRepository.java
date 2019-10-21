package com.hillel.evo.adviser.userprofile.repository;

import com.hillel.evo.adviser.entity.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {

}
