package com.sw.hearhere.domain.repository;


import com.sw.hearhere.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    boolean existsByProviderId(Long id);

    Optional<User> findByProviderId(Long providerId);


    Optional<User> findByEmail(String email);
}

