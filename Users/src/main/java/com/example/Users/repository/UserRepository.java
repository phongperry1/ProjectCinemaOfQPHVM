package com.example.Users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Users.entity.Users;





public interface UserRepository extends JpaRepository<Users, Integer>{

    Optional<Users> findById(int id);
}
