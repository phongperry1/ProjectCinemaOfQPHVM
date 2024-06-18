package com.example.mo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "cinemaOwnerID") // Tên cột khóa ngoại trong bảng Food
    private CinemaOwner cinemaOwner;
    @Column(length = 45, nullable = false, name = "FoodName")
    private String FoodName;
    @Column(name = "Price")
    private double Price;

    @Column(nullable = true, length = 64, name = "PhotosImagePath")
    private String PhotoFood;

}
