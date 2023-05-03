package com.wendy.blogapiwithspringsecurity.repositories;

import com.wendy.blogapiwithspringsecurity.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = "SELECT * FROM Token WHERE user_id = ?1 AND isExpired = 'false', isRevoked = 'false'", nativeQuery = true)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}
