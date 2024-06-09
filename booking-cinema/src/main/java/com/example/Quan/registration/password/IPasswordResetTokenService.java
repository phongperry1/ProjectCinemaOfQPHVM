package com.example.Quan.registration.password;

import java.util.Optional;

import com.example.Quan.mo.Users;

/**
 * @author Sampson Alfred
 */

public interface IPasswordResetTokenService {
    String validatePasswordResetToken(String theToken);

    Optional<Users> findUserByPasswordResetToken(String theToken);

    void resetPassword(Users theUser, String password);

    void createPasswordResetTokenForUser(Users user, String passwordResetToken);
}
