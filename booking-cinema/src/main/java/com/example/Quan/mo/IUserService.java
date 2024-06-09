package com.example.Quan.mo;

import java.util.List;
import java.util.Optional;

import com.example.Quan.registration.RegistrationRequest;

/**
 * @author Sampson Alfred
 */

public interface IUserService {
    List<User> getAllUsers();

    User registerUser(RegistrationRequest registrationRequest);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

}
