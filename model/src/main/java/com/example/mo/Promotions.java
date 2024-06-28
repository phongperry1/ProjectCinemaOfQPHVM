package com.example.mo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer PromotionID;

    @ManyToOne
    @JoinColumn(name = "CinemaOwnerID")
    private CinemaOwner cinemaOwner;
    private Integer MovieID;

    @Column(length = 50, nullable = false, name = "PromotionName")
    private String PromotionName;
    @Column(length = 100, nullable = false, name = "Description")
    private String Description;
    @Column(name = "DiscountRate")
    private Double DiscountRate;
    @Column(name = "StartDate")
    private Date StartDate;
    @Column(name = "EndDate")
    private Date EndDate;
    @Column(nullable = true, length = 64, name = "PhotosImagePath")
    private String PhotoPromotions;


    public Integer getPromotionID() {
        return this.PromotionID;
    }

    public void setPromotionID(Integer PromotionID) {
        this.PromotionID = PromotionID;
    }

     @Transient
    public String getPhotosImagePath() {
        if (PhotoPromotions == null) return null;

        return "/promotions-photo/" + PromotionID + "/" + PhotoPromotions;
    }


    public String getPhotoPromotions() {
        return this.PhotoPromotions;
    }

    public void setPhotoPromotions(String PhotoPromotions) {
        this.PhotoPromotions = PhotoPromotions;
    }




    
    public Integer getMovieID() {
        return this.MovieID;
    }

    public void setMovieID(Integer MovieID) {
        this.MovieID = MovieID;
    }

    public String getPromotionName() {
        return this.PromotionName;
    }

    public void setPromotionName(String PromotionName) {
        this.PromotionName = PromotionName;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Double getDiscountRate() {
        return this.DiscountRate;
    }

    public void setDiscountRate(Double DiscountRate) {
        this.DiscountRate = DiscountRate;
    }

    public Date getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }





}