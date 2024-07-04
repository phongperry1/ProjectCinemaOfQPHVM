package com.example.mo;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class CinemaOwnerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_owner_id")
    private CinemaOwner cinemaOwner;

    private String transactionType; // e.g., "Refund", "Transfer"
    private double amount;
    private Date transactionDate;

    // Getters and setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public CinemaOwner getCinemaOwner() {
        return cinemaOwner;
    }

    public void setCinemaOwner(CinemaOwner cinemaOwner) {
        this.cinemaOwner = cinemaOwner;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
