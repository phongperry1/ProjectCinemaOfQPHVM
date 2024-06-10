package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findById(int id);

    Users findByUserName(String username);

    public Users findByEmail(String emaill);

    public Users findByVerificationCode(String code);
}
