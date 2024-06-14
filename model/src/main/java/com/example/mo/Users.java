package com.example.mo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
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
    private Integer memberPoints = 0; // Default value to avoid null
    private String paymentMethod;
    private String userType;
    private String profileImageURL;

    private String resetPasswordToken;
    // Getters and Setters

}
