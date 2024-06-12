package com.example.CRUD.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.CinemaOwner;


public interface CinemaOwnerRepository extends JpaRepository<CinemaOwner, String> {
    List<CinemaOwner> findByCinemaNameContainingIgnoreCase(String cinemaName);
}


