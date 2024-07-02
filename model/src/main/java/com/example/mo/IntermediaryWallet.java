package com.example.mo;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class IntermediaryWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    private Date lastUpdated;

    @OneToMany(mappedBy = "intermediaryWallet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IntermediaryTransaction> transactions;

    // Default constructor
    public IntermediaryWallet() {
        this.balance = 0.0;
        this.lastUpdated = new Date();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<IntermediaryTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<IntermediaryTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addFunds(Double amount) {
        this.balance += amount;
        this.lastUpdated = new Date();
        addTransaction(amount, "Credit");
    }

    public void withdrawFunds(Double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            this.lastUpdated = new Date();
            addTransaction(amount, "Debit");
        } else {
            throw new IllegalArgumentException("Insufficient funds in the intermediary wallet.");
        }
    }

    private void addTransaction(Double amount, String type) {
        IntermediaryTransaction transaction = new IntermediaryTransaction(this, amount, new Date(), type);
        this.transactions.add(transaction);
    }
}
