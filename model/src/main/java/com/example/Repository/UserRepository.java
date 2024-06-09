package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.Users;





public interface UserRepository extends JpaRepository<Users, Integer>{

    Optional<Users> findById(int UserId);
    Users findByUserName(String username);
    Optional<Users> findByEmail(String email);
}

