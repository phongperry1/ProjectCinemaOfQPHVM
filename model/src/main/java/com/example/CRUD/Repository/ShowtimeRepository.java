package com.example.CRUD.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mo.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
    
    List<Showtime> findByMovieID(Integer movieID);
    
    List<Showtime> findByShowDate(Date showDate);
    
    List<Showtime> findByCinemaOwnerID(Integer cinemaOwnerID);
    
    List<Showtime> findByMovieIDAndCinemaOwnerID(Integer movieID, Integer cinemaOwnerID);

    @Query("SELECT s FROM Showtime s WHERE s.movie.movieID = :movieID")
    List<Showtime> findByMovie_MovieID(@Param("movieID") Integer movieID);

    @Query(value = "SELECT s.show_date, s.show_time, s.showtime_id FROM Showtime s JOIN Theater t ON s.theater_id = t.theater_id WHERE s.movie_id = :movieID AND t.theater_id = :theaterID", nativeQuery = true)
    List<Object[]> findByMovieIDAndTheaterID(@Param("movieID") Integer movieID, @Param("theaterID") Integer theaterID);

    @Query("SELECT s.showDate FROM Showtime s WHERE s.showtimeID = :showtimeId")
    Date findShowDateByShowtimeId(@Param("showtimeId") Integer showtimeId);

    @Query("SELECT s FROM Showtime s WHERE s.cinemaOwnerID = :cinemaOwnerID")
    Page<Showtime> findByCinemaOwnerID(@Param("cinemaOwnerID") Integer cinemaOwnerID, Pageable pageable);

    @Query("SELECT s FROM Showtime s WHERE s.movie.movieID = :movieID AND s.cinemaOwnerID = :cinemaOwnerID")
    Page<Showtime> findByMovieIDAndCinemaOwnerID(@Param("movieID") Integer movieID, @Param("cinemaOwnerID") Integer cinemaOwnerID, Pageable pageable);

    @Query("SELECT s FROM Showtime s WHERE s.showDate = :showDate")
    Page<Showtime> findByShowDate(@Param("showDate") Date showDate, Pageable pageable);
}
