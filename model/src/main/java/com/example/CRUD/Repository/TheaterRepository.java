package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mo.Theater;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByCinemaOwnerID(Integer cinemaOwnerID);

    @Query("SELECT t.cinemaOwnerID FROM Theater t WHERE t.theaterID = :theaterID")
    Integer findCinemaOwnerIdByTheaterId(@Param("theaterID") Integer theaterID);

    List<Theater> findByTheaterNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String theaterName,
            String address);

    List<Theater> findByAddressContainingIgnoreCase(String address);

    @Query("SELECT t FROM Theater t JOIN t.movies m WHERE m.movieID = :movieID")
    List<Theater> findTheatersByMovieID(@Param("movieID") Integer movieID);

}
