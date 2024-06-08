package com.example.Users.entity;


import lombok.Data;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
@Data
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID") 
    private Integer userId; 
    
    @Column(name = "UserName")  
    private String userName;
    
    @Column(name = "Phone")  
    private String phone;
    
    @Column(name = "Email")  
    private String email;
    
    @Column(name = "UserPassword") 
    private String userPassword; 
    
    @Column(name = "Birthdate")  
    private Date birthdate;
    
    @Column(name = "Location")  
    private String location;

    @Column(name = "ProfileImageURL")
    private String profileImageUrl;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "UserRank")
    private String userRank;

    @Column(name = "MemberPoints") 
    private String memberPoints; 

    @Column(name = "UserType")
    private String userType; 

}
