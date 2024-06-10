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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId; 

    private String userName;
    private String email;
    private String phone;
    private String userPassword;
    private String role;
    private boolean status;
    private String verificationCode;
    private Date birthdate;
    private String location;
    private String gender;
    private String userRank;
    private Integer memberPoints;
    private String paymentMethod;
    private String userType;
    private String profileImageURL;
    // Changed from `Status` to `status` for consistency with field naming
    // conventions
}

