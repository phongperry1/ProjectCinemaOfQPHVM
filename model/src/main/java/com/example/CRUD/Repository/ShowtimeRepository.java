package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mo.Showtime;

import java.sql.Date;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
    List<Showtime> findByMovieID(Integer movieID);
    List<Showtime> findByShowDate(Date showDate);
}
