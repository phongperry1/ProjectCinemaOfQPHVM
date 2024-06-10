// package com.example.Users.entity;

// import lombok.Data;


// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;

// import java.util.Date;
// import java.util.List;

// @Data
// @Entity
// @Table(name = "Movie")
// public class Movie {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "MovieID")
//     private Long movieID;

//     @Column(name = "Title", nullable = false)
//     private String title;

//     @Column(name = "Genre")
//     private String genre;

//     @Column(name = "Duration")
//     private Integer duration;

//     @Column(name = "Director")
//     private String director;

//     @Column(name = "Cast", columnDefinition = "NVARCHAR(255)")
//     private String cast;

//     @Column(name = "ReleaseDate")
//     private Date releaseDate;

//     @Column(name = "ShowTime")
//     private String showTime;

//     @Column(name = "Languages", columnDefinition = "NVARCHAR(255)")
//     private String languages;

//     @Column(name = "RatingCount")
//     private Integer ratingCount;

//     @Column(name = "AverageRating", precision = 3, scale = 2)
//     private Double averageRating;

//     @Column(name = "Description", columnDefinition = "TEXT")
//     private String description;

//     @Column(name = "TrailerURL", columnDefinition = "NVARCHAR(255)")
//     private String trailerURL;

//     @OneToMany(mappedBy = "movie")
//     private List<Rating> ratings;
// }