package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.mo.Seat;


public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByStatusSeat(boolean statusSeat);

    @Query("SELECT s.seatName, s.seatType, s.statusSeat " +"FROM Seat s " +
       "JOIN s.screeningRoom sr " +
       "JOIN s.showtime st " +
       "WHERE sr.ScreeningRoomID = :screeningRoomId " +
       "AND st.showtimeId = :showtimeId " +
       "AND s.statusSeat = true")
    List<Object[]> findBookedSeats(@Param("screeningRoomId") Integer screeningRoomId, 
                                  @Param("showtimeId") Integer showtimeId);
}
