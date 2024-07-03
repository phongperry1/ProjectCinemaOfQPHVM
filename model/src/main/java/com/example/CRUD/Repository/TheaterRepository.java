package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mo.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByCinemaOwnerID(Integer cinemaOwnerID);

    @Query("SELECT t.cinemaOwnerID FROM Theater t WHERE t.theaterID = :theaterId")
    Integer findCinemaOwnerIdByTheaterId(Integer theaterId);
}
