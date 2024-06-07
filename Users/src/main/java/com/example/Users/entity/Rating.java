package com.example.Users.entity;

import lombok.Data;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "Ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatingID")
    private Long ratingID;

    @Column(name = "MovieID", nullable = false)
    private Long movieID;

    @Column(name = "UserID", nullable = false)
    private Long userID;

    @Column(name = "RatingValue", precision = 3, scale = 2, nullable = false)
    private Double ratingValue;

    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "RatingDate", nullable = false)
    private LocalDateTime ratingDate;

    @ManyToOne
    @JoinColumn(name = "MovieID", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private Users users;
}
