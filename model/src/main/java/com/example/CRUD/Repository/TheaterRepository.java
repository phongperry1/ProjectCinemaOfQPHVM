package com.example.CRUD.Repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mo.Theater;

@Repository
public interface TheaterRepository extends JpaRepository <Theater ,Integer>{
    @Query("SELECT t.CinemaOwnerID FROM Theater t WHERE t.TheaterID = :theaterId")
    Integer findCinemaOwnerIdByTheaterId(Integer theaterId);
}