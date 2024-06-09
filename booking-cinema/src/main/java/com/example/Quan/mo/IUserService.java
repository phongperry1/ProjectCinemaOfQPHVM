package com.example.Quan.mo;

import java.util.List;
import java.util.Optional;

import com.example.Quan.registration.RegistrationRequest;

/**
 * @author Sampson Alfred
 */

public interface IUserService {
    List<Users> getAllUsers();

    Users registerUser(RegistrationRequest registrationRequest);

    Optional<Users> findByEmail(String email);

    Optional<Users> findById(Integer userID);

}
