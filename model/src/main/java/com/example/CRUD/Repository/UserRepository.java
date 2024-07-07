package com.example.CRUD.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.mo.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findById(int id);

    Users findByUserName(String username);

    Users findByEmail(String email);

    Users findByVerificationCode(String code);

    Users findByResetPasswordToken(String token);
    List<Users>findByRole(String role);
    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = :role")
    long countByRole(@Param("role") String role);

}
