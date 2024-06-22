package com.example.mo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
public class CinemaOwner  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinemaOwnerID;
    @OneToOne
    @JoinColumn(name = "UserId")
    private Users users;

    private String cinemaName;
    private String AddressCinema;   
=======
public class CinemaOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CinemaOwnerID;
    private String CinemaName;
    private String AddressCinema;
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
    private String Hotline;
    private String Email;
    private int EmployeeID;
}