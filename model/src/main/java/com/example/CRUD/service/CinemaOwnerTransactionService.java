package com.example.CRUD.service;

import com.example.CRUD.Repository.CinemaOwnerTransactionRepository;
import com.example.mo.CinemaOwnerTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaOwnerTransactionService {

    @Autowired
    private CinemaOwnerTransactionRepository repository;

    public List<CinemaOwnerTransaction> getTransactionsByCinemaOwnerId(int cinemaOwnerId) {
        return repository.findByCinemaOwnerCinemaOwnerID(cinemaOwnerId);
    }

    public CinemaOwnerTransaction saveTransaction(CinemaOwnerTransaction transaction) {
        return repository.save(transaction);
    }
}
