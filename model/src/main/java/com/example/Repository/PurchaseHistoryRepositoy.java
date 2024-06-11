package com.example.Repository;


import com.example.mo.PurchaseHistory;
import com.example.mo.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepositoy extends JpaRepository<PurchaseHistory, Integer> {
    List<PurchaseHistory> findByUser(Users user);
}

