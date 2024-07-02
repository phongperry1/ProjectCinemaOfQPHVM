package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.IntermediaryTransaction;

public interface IntermediaryTransactionRepository extends JpaRepository<IntermediaryTransaction, Long> {
}
