package com.example.CRUD.Repository;

import com.example.mo.Theater;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByCinemaOwnerID(Integer cinemaOwnerID);

    @Query("SELECT t.cinemaOwnerID FROM Theater t WHERE t.theaterID = :theaterID")
    Integer findCinemaOwnerIdByTheaterId(@Param("theaterID") Integer theaterID);
}
