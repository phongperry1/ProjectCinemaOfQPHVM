package com.example.CRUD.Repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mo.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer > {
    List<Showtime> findByShowDate(Date ShowDate);
}