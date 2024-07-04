package com.example.mo;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int foodID;

    @Column(nullable = false)
    private int cinemaOwnerID;

    @Column(length = 45, nullable = false)
    private String foodName;

    @Column(name = "price")
    private double price;

    @Column(nullable = true, length = 64)
    private String photoFood;

    @ManyToMany(mappedBy = "foods", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Ticket> tickets;

    @Transient
    public String getPhotosImagePath() {
        if (photoFood == null) return null;
        return "/food-photo/" + foodID + "/" + photoFood;
    }

    // Getters and setters
    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getCinemaOwnerID() {
        return cinemaOwnerID;
    }

    public void setCinemaOwnerID(int cinemaOwnerID) {
        this.cinemaOwnerID = cinemaOwnerID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotoFood() {
        return photoFood;
    }

    public void setPhotoFood(String photoFood) {
        this.photoFood = photoFood;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
