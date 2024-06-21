package com.example.mo;

import jakarta.persistence.Column;
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
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theaterID;

    @Column(nullable = false)
    private Integer cinemaOwnerID;

    @Column(length = 45, nullable = false)
    private String theaterName;

    @Column(length = 2000, nullable = false)
    private String map;

    @Column(length = 1000, nullable = false)
    private String address;

    @Column(length = 2000, nullable = false)
    private String description;

    @Column(nullable = true, length = 64)
    private String photoTheater;

    @Column(nullable = false)
    private Double rating;

    // Getters and Setters
    public Integer getTheaterID() {
        return theaterID;
    }

    public void setTheaterID(Integer theaterID) {
        this.theaterID = theaterID;
    }

    public Integer getCinemaOwnerID() {
        return cinemaOwnerID;
    }

    public void setCinemaOwnerID(Integer cinemaOwnerID) {
        this.cinemaOwnerID = cinemaOwnerID;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoTheater() {
        return photoTheater;
    }

    public void setPhotoTheater(String photoTheater) {
        this.photoTheater = photoTheater;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
