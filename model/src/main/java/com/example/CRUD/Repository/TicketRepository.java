package com.example.CRUD.Repository;

import com.example.mo.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByUserUserId(int userId);

    @Query("SELECT t FROM Ticket t JOIN FETCH t.movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :movieTitle, '%'))")
    List<Ticket> findTicketsByMovieTitleContainingIgnoreCase(@Param("movieTitle") String movieTitle);

    @Query("SELECT t FROM Ticket t JOIN FETCH t.movie")
    List<Ticket> findAllTicketsWithMovies();
}
