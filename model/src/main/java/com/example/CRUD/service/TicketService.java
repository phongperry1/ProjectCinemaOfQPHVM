package com.example.CRUD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.TicketRepository;
import com.example.mo.Ticket;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    
    public List<Ticket> getTicketsByUserId(int userId) {
        return ticketRepository.findByUserUserId(userId);
    }
}
