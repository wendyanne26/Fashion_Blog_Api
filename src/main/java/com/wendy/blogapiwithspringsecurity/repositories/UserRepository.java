package com.wendy.blogapiwithspringsecurity.repositories;

import com.wendy.blogapiwithspringsecurity.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT * from users WHERE username = ?1", nativeQuery = true)
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);
}
