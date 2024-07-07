package com.example.CRUD.service;

import com.example.CRUD.Repository.TicketRepository;
import com.example.CRUD.Repository.UserByAdminRepository;
import com.example.mo.Ticket;
import com.example.mo.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserByAdminService {

    @Autowired
    private UserByAdminRepository userByAdminRepository;
    private TicketService ticketService;
    private TicketRepository ticketRepository;

    public List<Users> getUserByAdmins() {
        return userByAdminRepository.findAll();
    }

    public Users addTicket(Users userByAdmin) {
        return userByAdminRepository.save(userByAdmin);
    }

    public Users findById(Integer UserId) {
        return userByAdminRepository.findById(UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUserProfile(Users userByAdmin) {
        userByAdminRepository.save(userByAdmin);
    }

    public void updateRankUser(Users userByAdmin) {
        userByAdminRepository.save(userByAdmin);
    }

    public List<Users> searchUsersByName(String userName) {
        if (userName == null || userName.isEmpty()) {
            return userByAdminRepository.findAll();
        } else {
            return userByAdminRepository.findByUserNameContainingIgnoreCase(userName);
        }
    }

    public List<Ticket> searchTicketByMoveName(String movieName) {
        if (movieName == null || movieName.isEmpty()) {
            return ticketService.getAllTickets();
        } else {
            return ticketRepository.findTicketsByMovieTitleContainingIgnoreCase(movieName);
        }
    }
}
