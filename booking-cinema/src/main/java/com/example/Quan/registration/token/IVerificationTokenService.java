package com.example.Quan.registration.token;

import java.util.Optional;

import com.example.Quan.mo.User;

/**
 * @author Sampson Alfred
 */

public interface IVerificationTokenService {
    String validateToken(String token);

    void saveVerificationTokenForUser(User user, String token);

    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(Long id);
}
