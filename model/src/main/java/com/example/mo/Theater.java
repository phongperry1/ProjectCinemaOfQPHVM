package com.example.mo;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Integer theaterID;

    @Column(nullable = false)
    private Integer cinemaOwnerID;

    @Column(name = "movie_id", nullable = false)
    private Integer movieID;


    @Column(length = 45, nullable = false)
    private String theaterName;

    @Column(length = 1000, nullable = false)
    private String address;

    @Column(length = 2000, nullable = false)
    private String description;

    @Column(nullable = true, length = 64, name = "photoTheater")
    private String photoTheater;

    @Column(nullable = false)
    private Double rating;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Showtime> showtimes;
    
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movie> movies;
    // Getter and setter for theaterID
    public Integer getTheaterID() {
        return theaterID;
    }

    public void setTheaterID(Integer theaterID) {
        this.theaterID = theaterID;
    }

    // Getter and setter for cinemaOwnerID
    public Integer getCinemaOwnerID() {
        return cinemaOwnerID;
    }

    public void setCinemaOwnerID(Integer cinemaOwnerID) {
        this.cinemaOwnerID = cinemaOwnerID;
    }

    // Getter and setter for movieID
    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    // Getter and setter for theaterName
    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    // Getter and setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Transient method to construct photo path
    @Transient
    public String getPhotosImagePath() {
        if (photoTheater == null) return null;
        return "/theater-photo/" + theaterID + "/" + photoTheater;
    }

    // Getter and setter for photoTheater
    public String getPhotoTheater() {
        return photoTheater;
    }

    public void setPhotoTheater(String photoTheater) {
        this.photoTheater = photoTheater;
    }

    // Getter and setter for rating
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    // Getter and setter for showtimes
    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
