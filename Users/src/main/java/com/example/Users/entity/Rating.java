// package com.example.Users.entity;

// import lombok.Data;

// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;

// @Data
// @Entity
// @Table(name = "Ratings")
// public class Rating {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "RatingID")
//     private Long ratingID;

//     @Column(name = "MovieID")
//     private Long movieID;

//     @Column(name = "UserID")
//     private Long userID;

//     @Column(name = "RatingValue")
//     private Double ratingValue;

//     @Column(name = "Comment")
//     private String comment;

//     @Column(name = "RatingDate")
//     private LocalDateTime ratingDate;

//     @ManyToOne
//     @JoinColumn(name = "MovieID")
//     private Movie movie;

//     @ManyToOne
//     @JoinColumn(name = "UserID")
//     private Users users;
// }
