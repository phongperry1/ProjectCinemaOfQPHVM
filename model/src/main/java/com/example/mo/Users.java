package com.example.mo;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")  // Ensures the table name is correctly mapped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;  
    private String userName;
    private String phone;
    private String email;
    private String userPassword;
    private Date birthdate;
    private String location;
    private String gender;
    private String userRank;
    private int memberPoints;
    private String paymentMethod;
    private String userType;
    private String profileImageURL;
    private boolean status;  
}
