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
    private int foodID;

    @Column(nullable = false)
    private int cinemaOwnerID;

    @Column(length = 45, nullable = false)
    private String foodName;

    @Column(name = "price")
    private double price;

    @Column(nullable = true, length = 64)
    private String photoFood;

    @Transient
    public String getPhotosImagePath() {
        if (photoFood == null) return null;
        return "/food-photo/" + foodID + "/" + photoFood;
    }
}
