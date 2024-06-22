package com.example.mo;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
=======
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
<<<<<<< HEAD

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieID;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "director")
    private String director;

    @Column(name = "cast")
    private String cast;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "releaseDate")
    private Date releaseDate;

    // @Column(name = "showTime")
    // private String showTime;

    @Column(name = "languages")
    private String languages;

    @Column(name = "ratingCount")
    private Integer ratingCount = 0;
    
    @Column(name = "averageRating")
    private Double averageRating = 0.0;
    @Column(name = "description")
    private String description;

    @Column(name = "trailerURL")
    private String trailerURL;

    @Column(name = "address")
    private String address;  

    @Column(name = "status_movie")
    private String statusMovie;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;

    // Getter và Setter cho address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public enum StatusMovie {
        NOW_SHOWING("Đang Chiếu"),
        COMING_SOON("Sắp Chiếu");
    
        private final String status;
    
        StatusMovie(String status) {
            this.status = status;
        }
    
        public String getStatus() {
            return status;
        }
    }
    
    public void updateDetails(Movie movieDetails) {
        this.title = movieDetails.getTitle();
        this.genre = movieDetails.getGenre();
        this.duration = movieDetails.getDuration();
        this.director = movieDetails.getDirector();
        this.cast = movieDetails.getCast();
        this.releaseDate = movieDetails.getReleaseDate();
        // this.showTime = movieDetails.getShowTime(); 
        this.languages = movieDetails.getLanguages();
        this.ratingCount = movieDetails.getRatingCount();
        this.averageRating = movieDetails.getAverageRating();
        this.description = movieDetails.getDescription();
        this.trailerURL = movieDetails.getTrailerURL();
        this.address = movieDetails.getAddress();  // Cập nhật thuộc tính này
    }
}
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MovieID;

    @Column(length = 100 ,name = "Title", nullable = false)
    private String Title;
    @Column(length = 45 ,name = "Genre", nullable = false)
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
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
