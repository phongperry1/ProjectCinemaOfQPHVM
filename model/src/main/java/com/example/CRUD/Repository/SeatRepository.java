package com.example.CRUD.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.mo.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s.seatName, s.seatType, s.statusSeat FROM Seat s WHERE s.screeningRoom.screeningRoomID = :screeningRoomId AND s.showtime.showtimeID = :showtimeId AND s.statusSeat = true")
    List<Object[]> findBookedSeats(@Param("screeningRoomId") Integer screeningRoomId, @Param("showtimeId") Integer showtimeId);
}
