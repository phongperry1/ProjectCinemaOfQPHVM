package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.Seat;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByStatusSeat(boolean statusSeat);
}
