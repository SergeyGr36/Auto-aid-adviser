package com.hillel.evo.adviser.registration.repository;

import com.hillel.evo.adviser.registration.dto.UserDto;
import com.hillel.evo.adviser.registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    UserDto findByMail(String mail);

    Boolean existsByMail(String mail);

    User findByActivationCode(String code);
}
