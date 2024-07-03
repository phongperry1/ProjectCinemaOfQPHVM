// package com.example.mo;

// import jakarta.persistence.*;
// import java.util.Date;

// @Entity
// public class IntermediaryTransaction {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @JoinColumn(name = "wallet_id", nullable = false)
//     private IntermediaryWallet intermediaryWallet;

//     private Double amount;

//     private Date transactionDate;

//     private String transactionType; // "Credit" or "Debit"

//     // Default constructor
//     public IntermediaryTransaction() {}

//     // Constructor with all fields
//     public IntermediaryTransaction(IntermediaryWallet intermediaryWallet, Double amount, Date transactionDate, String transactionType) {
//         this.intermediaryWallet = intermediaryWallet;
//         this.amount = amount;
//         this.transactionDate = transactionDate;
//         this.transactionType = transactionType;
//     }

//     // Getters and setters
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public IntermediaryWallet getIntermediaryWallet() {
//         return intermediaryWallet;
//     }

//     public void setIntermediaryWallet(IntermediaryWallet intermediaryWallet) {
//         this.intermediaryWallet = intermediaryWallet;
//     }

//     public Double getAmount() {
//         return amount;
//     }

//     public void setAmount(Double amount) {
//         this.amount = amount;
//     }

//     public Date getTransactionDate() {
//         return transactionDate;
//     }

//     public void setTransactionDate(Date transactionDate) {
//         this.transactionDate = transactionDate;
//     }

//     public String getTransactionType() {
//         return transactionType;
//     }

//     public void setTransactionType(String transactionType) {
//         this.transactionType = transactionType;
//     }
// }