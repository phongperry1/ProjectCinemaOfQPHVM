package com.example.Quan.registration.token;

import java.util.Optional;

import com.example.Quan.mo.Users;

/**
 * @author Sampson Alfred
 */

public interface IVerificationTokenService {
    String validateToken(String token);

    void saveVerificationTokenForUser(Users user, String token);

    Optional<VerificationToken> findByToken(String token);

}
