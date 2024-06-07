package com.example.Repository;
import com.example.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface UserByAdminRepository extends JpaRepository<Users, String> {
    List<Users> findByUserNameContainingIgnoreCase(String userName);
}
