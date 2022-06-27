package com.example.security.repository;

import com.example.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<AppUser> fetchUserByUsername(@Param("username") String username);
}