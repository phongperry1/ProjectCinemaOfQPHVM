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
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MovieID;
    private String Title;
    private String Genre;
    private int Duration;
    private String Director;
    private String Cast;
    private Date ReleaseDate;
    private String Showtime;
    private String Languages;
    private int RatingCount;
    private double AverageRating;
    private String Description;
    private String TrailerURL;
    private String Address;

    
}