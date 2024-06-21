package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUserId(Integer userId);
}
