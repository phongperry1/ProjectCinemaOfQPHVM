package com.example.CRUD.Repository;

import com.example.mo.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    // List<Ticket> findByMovieID(int movieID);
    List<Ticket> findByUserUserId(int userId);
}
