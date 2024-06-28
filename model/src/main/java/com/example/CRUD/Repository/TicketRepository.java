package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.Ticket;

public interface TicketRepository extends JpaRepository <Ticket ,Integer> {
    
}
