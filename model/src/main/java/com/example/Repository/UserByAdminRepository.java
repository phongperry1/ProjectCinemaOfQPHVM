package com.example.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.Users;

import java.util.List;
public interface UserByAdminRepository extends JpaRepository<Users, Integer> {
    List<Users> findByUserNameContainingIgnoreCase(String userName);
}
