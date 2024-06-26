package com.example.mo;

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
public class CinemaOwnerRequest {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int cinemaOwnerRequestId;
@ManyToOne
@JoinColumn(name = "userId")
private Users users;
private String cinemaName;
private String addressCinema;
private String hotline;
private String email;
private int employeeID;
}