package com.example.Quan.registration.password;

import java.util.Optional;

import com.example.Quan.mo.User;

/**
 * @author Sampson Alfred
 */

public interface IPasswordResetTokenService {
    String validatePasswordResetToken(String theToken);

    Optional<User> findUserByPasswordResetToken(String theToken);

    void resetPassword(User theUser, String password);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
}
