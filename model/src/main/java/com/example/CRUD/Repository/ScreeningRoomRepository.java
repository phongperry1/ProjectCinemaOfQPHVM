package com.example.CRUD.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mo.ScreeningRoom;

@Repository
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Integer> {
    @Query("SELECT new ScreeningRoom(r.ScreeningRoomID, r.Roomname) FROM ScreeningRoom r JOIN Showtime s ON r.ScreeningRoomID = s.screeningRoom.ScreeningRoomID " +
    "WHERE s.theater.TheaterID = :theaterId AND s.movie.movieID = :movieId AND s.showTime = :showTime AND s.showDate = :showDate")
    List<ScreeningRoom> findScreeningRoomsByTheaterIdAndMovieIdAndShowTimeAndShowDate(@Param("theaterId") int theaterId,
                                                                                      @Param("movieId") int movieId,
                                                                                      @Param("showTime") Time showTime,
                                                                                      @Param("showDate") Date showDate);
}