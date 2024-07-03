package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mo.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByCinemaOwnerID(Integer cinemaOwnerID);

    @Query("SELECT f FROM Food f WHERE f.cinemaOwnerID = :cinemaOwnerId")
    List<Food> getFoodByCinemaOwnerId(@Param("cinemaOwnerId") int cinemaOwnerId);
}
