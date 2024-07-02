package com.example.mo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinemaOwnerID;

    @OneToOne
    @JoinColumn(name = "UserId")
    private Users users;

    private String cinemaName;
    private String AddressCinema;
    private String Hotline;
    private String Email;
    private int EmployeeID;

    // Wallet attributes
    private Double walletBalance = 0.0;
    private Date walletLastUpdated = new Date();

    // Methods to manage the wallet
    public void addFundsToWallet(Double amount) {
        if (amount > 0) {
            this.walletBalance += amount;
            this.walletLastUpdated = new Date();
        } else {
            throw new IllegalArgumentException("Amount to add must be greater than zero.");
        }
    }

    public void withdrawFundsFromWallet(Double amount) {
        if (amount > 0 && this.walletBalance >= amount) {
            this.walletBalance -= amount;
            this.walletLastUpdated = new Date();
        } else {
            throw new IllegalArgumentException("Insufficient funds or invalid amount.");
        }
    }

    @Override
    public String toString() {
        return "CinemaOwner{" +
                "cinemaOwnerID=" + cinemaOwnerID +
                ", cinemaName='" + cinemaName + '\'' +
                ", AddressCinema='" + AddressCinema + '\'' +
                ", Hotline='" + Hotline + '\'' +
                ", Email='" + Email + '\'' +
                ", EmployeeID=" + EmployeeID +
                ", walletBalance=" + walletBalance +
                ", walletLastUpdated=" + walletLastUpdated +
                '}';
    }
}
