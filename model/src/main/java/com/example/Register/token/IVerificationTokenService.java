package com.example.Register.token;

import java.util.Optional;

import com.example.mo.Users;

/**
 * @author Sampson Alfred
 */

public interface IVerificationTokenService {
    String validateToken(String token);

    void saveVerificationTokenForUser(Users user, String token);

    Optional<VerificationToken> findByToken(String token);

}