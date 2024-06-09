package com.example.Register.password;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Sampson Alfred
 */

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String theToken);
}