package com.example.mo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private int CinemaOwnerID;
    private String CinemaName;
    private String AddressCinema;
    private String Hotline;
    private String Email;
    private String PromotionProgram;
    private int EmployeeID;


}