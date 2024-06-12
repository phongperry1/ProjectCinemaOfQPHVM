package com.example.CRUD.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.PurchaseHistory;
import com.example.mo.Users;

import java.util.List;

public interface PurchaseHistoryRepositoy extends JpaRepository<PurchaseHistory, Integer> {
    List<PurchaseHistory> findByUser(Users user);
}

