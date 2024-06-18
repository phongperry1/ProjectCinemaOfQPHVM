package com.example.mo;

import java.sql.Date;

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
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int RatingID;
    @ManyToOne
    @JoinColumn(name = "movieID")
    private Movie movie; // Thay vì int MovieID

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users user; // Thay vì int UserID
    private double RatingValue;
    private String Comment;
    private Date RatingDate;

}
