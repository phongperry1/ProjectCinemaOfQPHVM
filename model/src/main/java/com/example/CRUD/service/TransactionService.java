package com.example.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mo.Transaction;
import com.example.CRUD.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        logger.info("Transaction added: " + transaction.toString());
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        logger.info("Retrieved all transactions: " + transactions.size());
        return transactions;
    }

    public List<Transaction> getTransactionsByUserId(Integer userId) {
        List<Transaction> transactions = transactionRepository.findByUserUserId(userId);
        logger.info("Retrieved transactions for user ID " + userId + ": " + transactions.size());
        return transactions;
    }
}
