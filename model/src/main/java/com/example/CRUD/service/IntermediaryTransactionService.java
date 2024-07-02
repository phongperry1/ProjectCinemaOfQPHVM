package com.example.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mo.IntermediaryTransaction;
import com.example.CRUD.Repository.IntermediaryTransactionRepository;

@Service
public class IntermediaryTransactionService {

    @Autowired
    private IntermediaryTransactionRepository intermediaryTransactionRepository;

    public IntermediaryTransaction saveTransaction(IntermediaryTransaction transaction) {
        return intermediaryTransactionRepository.save(transaction);
    }
}
