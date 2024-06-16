package com.example.mo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaOwner  {
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
}