package com.example.mo;

import java.sql.Date;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private int userId;  // Changed from `UserId` to `userId` for consistency with field naming conventions

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
    private boolean status = true; 
    private boolean isEnabled = false;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    private Role role;
}

