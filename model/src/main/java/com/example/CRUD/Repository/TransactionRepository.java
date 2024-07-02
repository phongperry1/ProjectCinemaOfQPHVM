package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUserId(Integer userId);
    Optional<Transaction> findByTransactionNo(String transactionNo);
}
