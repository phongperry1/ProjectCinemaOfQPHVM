package com.example.Register.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sampson Alfred
 */

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    Optional<VerificationToken> findByToken(String token);

}