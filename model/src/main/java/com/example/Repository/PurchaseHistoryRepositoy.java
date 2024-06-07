package com.example.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.PurchaseHistory;
import com.example.entity.Users;

import java.util.List;

public interface PurchaseHistoryRepositoy extends JpaRepository<PurchaseHistory, String> {
    List<PurchaseHistory> findByUser(Users user);
}

