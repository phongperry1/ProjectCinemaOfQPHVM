package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.mo.Users;

public interface UserLoginRepository extends JpaRepository<Users, Integer> {
    // Optional<Users> findByEmail(String email);

    // @Modifying
    // @Query(value = "UPDATE User u set u.username =:userName," + "u.email =:email where u.user_id =:user_id")
    // void update(String userName, String email, int userId );

}