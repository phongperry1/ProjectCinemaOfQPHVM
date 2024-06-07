package com.example.mo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int FoodID;
    private int CinemaOwnerID;
    @Column(length = 45, nullable = false, name = "FoodName")
    private String FoodName;
    @Column(name = "Price")
    private double Price;
    @Column(nullable = true, length = 64, name = "PhotosImagePath")
    private String PhotoFood;


    public int getFoodID() {
        return this.FoodID;
    }

    public void setFoodID(int FoodID) {
        this.FoodID = FoodID;
    }

    public int getCinemaOwnerID() {
        return this.CinemaOwnerID;
    }

    public void setCinemaOwnerID(int CinemaOwnerID) {
        this.CinemaOwnerID = CinemaOwnerID;
    }

    public String getFoodName() {
        return this.FoodName;
    }

    public void setFoodName(String FoodName) {
        this.FoodName = FoodName;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    @Transient
    public String getPhotosImagePath() {
        if (PhotoFood == null) return null;

        return "/food-photo/" + FoodID + "/" + PhotoFood;
    }


    public String getPhotoFood() {
        return this.PhotoFood;
    }

    public void setPhotoFood(String PhotoFood) {
        this.PhotoFood = PhotoFood;
    }
   
}