package com.example.CRUD.Repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mo.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer > {
    List<Showtime> findByShowDate(Date ShowDate);

    List<Showtime> findByMovie_MovieID(Integer movieID);

    @Query(value = "SELECT s.show_date, s.show_time, s.showtimeid FROM Showtime s JOIN Theater t ON s.theaterid = t.theaterid WHERE s.movieid = :movieID AND t.theaterid = :theaterID", nativeQuery = true)
    List<Object[]> findByMovieIDAndTheaterID(@Param("movieID") Integer movieID, @Param("theaterID") Integer theaterID);
}