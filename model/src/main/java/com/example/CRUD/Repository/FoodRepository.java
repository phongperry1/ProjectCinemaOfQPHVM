package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mo.Food;
import java.util.List;


@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>{
    @Query("SELECT f FROM Food f WHERE f.CinemaOwnerID = :cinemaOwnerId")
    List<Food> getFoodByCinemaOwnerId(@Param("cinemaOwnerId") int cinemaOwnerId);
}