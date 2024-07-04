package com.example.CRUD.Repository;

import com.example.mo.CancelledRequest;
import com.example.mo.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CancelledRequestRepository extends JpaRepository<CancelledRequest, Integer> {
    List<CancelledRequest> findByStatus(String status);
    List<CancelledRequest> findByTicketUserUserId(int userId);
    List<CancelledRequest> findByTicketTicketId(int ticketId);
    void deleteAllByTicket(Ticket ticket);
}
