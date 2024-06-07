package com.example.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PurchaseID;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private Users user;
    private int TicketId;
    private Date PurchaseDate;

}
