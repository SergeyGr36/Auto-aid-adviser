package com.hillel.evo.adviser.registration.repositories;

import com.hillel.evo.adviser.registration.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
