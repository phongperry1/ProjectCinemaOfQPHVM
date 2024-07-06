package com.example.CRUD.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findById(int id);

    Users findByUserName(String username);

    Users findByEmail(String email);

    Users findByVerificationCode(String code);

    Users findByResetPasswordToken(String token);
}
