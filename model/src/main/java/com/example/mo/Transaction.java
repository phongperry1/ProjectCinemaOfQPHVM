package com.example.mo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String bankCode;
    private String payDate;
    private String transactionNo;
    private String tmnCode;
    private String orderInfo;
    private String txnRef;
    private double amount;
    private String cardType;
    private String transactionStatus;
    private String bankTranNo;
    private String responseCode;
    private String transactionType; // New field to indicate "Credit" or "Debit"

    // Default constructor
    public Transaction() {}

    // Constructor with all fields
    public Transaction(Users user, String bankCode, String payDate, String transactionNo, String tmnCode,
                       String orderInfo, String txnRef, double amount, String cardType,
                       String transactionStatus, String bankTranNo, String responseCode, String transactionType) {
        this.user = user;
        this.bankCode = bankCode;
        this.payDate = payDate;
        this.transactionNo = transactionNo;
        this.tmnCode = tmnCode;
        this.orderInfo = orderInfo;
        this.txnRef = txnRef;
        this.amount = amount;
        this.cardType = cardType;
        this.transactionStatus = transactionStatus;
        this.bankTranNo = bankTranNo;
        this.responseCode = responseCode;
        this.transactionType = transactionType;
    }

    // Getters and setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPayDate() {
        return this.payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getTransactionNo() {
        return this.transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTmnCode() {
        return this.tmnCode;
    }

    public void setTmnCode(String tmnCode) {
        this.tmnCode = tmnCode;
    }

    public String getOrderInfo() {
        return this.orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getTxnRef() {
        return this.txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getBankTranNo() {
        return this.bankTranNo;
    }

    public void setBankTranNo(String bankTranNo) {
        this.bankTranNo = bankTranNo;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
