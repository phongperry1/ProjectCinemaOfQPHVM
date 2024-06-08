package com.example.Minh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

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

    @Column(name = "showTime")
    private String showTime;

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
        this.showTime = movieDetails.getShowTime(); 
        this.languages = movieDetails.getLanguages();
        this.ratingCount = movieDetails.getRatingCount();
        this.averageRating = movieDetails.getAverageRating();
        this.description = movieDetails.getDescription();
        this.trailerURL = movieDetails.getTrailerURL();
        this.address = movieDetails.getAddress();  // Cập nhật thuộc tính này
    }
}
