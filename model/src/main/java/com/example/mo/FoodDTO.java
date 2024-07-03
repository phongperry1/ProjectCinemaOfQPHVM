package com.example.mo;

public class FoodDTO {
    private int foodID;
    private int cinemaOwnerID;
    private String foodName;
    private double price;
    private String photoFood;

    // Constructor with parameters
    public FoodDTO(int foodID, int cinemaOwnerID, String foodName, double price, String photoFood) {
        this.foodID = foodID;
        this.cinemaOwnerID = cinemaOwnerID;
        this.foodName = foodName;
        this.price = price;
        this.photoFood = photoFood;
    }

    // Default constructor
    public FoodDTO() {
    }

    // Getter and Setter for foodID
    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    // Getter and Setter for cinemaOwnerID
    public int getCinemaOwnerID() {
        return cinemaOwnerID;
    }

    public void setCinemaOwnerID(int cinemaOwnerID) {
        this.cinemaOwnerID = cinemaOwnerID;
    }

    // Getter and Setter for foodName
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter and Setter for photoFood
    public String getPhotoFood() {
        return photoFood;
    }

    public void setPhotoFood(String photoFood) {
        this.photoFood = photoFood;
    }
}