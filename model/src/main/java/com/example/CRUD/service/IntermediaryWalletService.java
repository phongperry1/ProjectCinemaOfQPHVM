package com.example.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mo.IntermediaryWallet;
import com.example.CRUD.Repository.IntermediaryWalletRepository;

@Service
public class IntermediaryWalletService {

    @Autowired
    private IntermediaryWalletRepository intermediaryWalletRepository;

    public void addFunds(Double amount) {
        IntermediaryWallet wallet = intermediaryWalletRepository.findById(1L).orElse(new IntermediaryWallet());
        wallet.addFunds(amount);
        intermediaryWalletRepository.save(wallet);
    }

    public void withdrawFunds(Double amount) {
        IntermediaryWallet wallet = intermediaryWalletRepository.findById(1L).orElse(new IntermediaryWallet());
        if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in intermediary wallet.");
        }
        wallet.withdrawFunds(amount);
        intermediaryWalletRepository.save(wallet);
    }

    public Double getBalance() {
        IntermediaryWallet wallet = intermediaryWalletRepository.findById(1L).orElse(new IntermediaryWallet());
        return wallet.getBalance() != null ? wallet.getBalance() : 0.0;
    }

    public IntermediaryWallet getIntermediaryWallet() {
        return intermediaryWalletRepository.findById(1L).orElse(new IntermediaryWallet());
    }
}
