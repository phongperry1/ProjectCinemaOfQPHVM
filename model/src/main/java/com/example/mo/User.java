package com.example.mo;

import java.sql.Date;

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

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserId;
    private String UserName;
    private String Phone;
    private String Email;
    private String UserPassword;
    private Date Birthdate;
    private String Location;
    private String Gender;
    private String UserRank;
    private int MemberPoints;
    private String PaymentMethod;
    private String UserType;
    private String ProfileImageURL;

 
}