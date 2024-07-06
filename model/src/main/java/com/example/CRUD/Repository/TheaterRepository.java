package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByCinemaOwnerID(Integer cinemaOwnerID);
}
