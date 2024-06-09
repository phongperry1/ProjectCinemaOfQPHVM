package com.example.Quan.mo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Sampson Alfred
 */

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE User u set u.userName =:userName," +
            "u.email =:email where u.id =:userID")
    void update(@Param("userName") String userName, @Param("email") String email, @Param("userID") Integer userID);

}
