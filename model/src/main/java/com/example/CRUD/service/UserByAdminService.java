package com.example.CRUD.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.TicketRepository;
import com.example.CRUD.Repository.UserByAdminRepository;
import com.example.mo.Ticket;
import com.example.mo.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserByAdminService {

    @Autowired
    private UserByAdminRepository userByAdminRepository;
    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private Timestamp lastResetTime;

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

    public String updateAllUsers() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (lastResetTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastResetTime);
            // cal.add(Calendar.DAY_OF_MONTH, 30);
            cal.add(Calendar.MINUTE, 1);
            Timestamp thirtyDaysLater = new Timestamp(cal.getTimeInMillis());

            if (currentTime.before(thirtyDaysLater)) {
                return "Reset not allowed. Can only reset once every 30 days.";
            }
        }

        List<Users> users = userByAdminRepository.findAll();
        for (Users user : users) {
            user.setMemberPoints(0);
            user.setUserRank("None");
        }
        userByAdminRepository.saveAll(users);
        lastResetTime = currentTime;
        return "Users updated successfully.";
    }

    public boolean isResetAllowed() {
        if (lastResetTime == null) {
            return true;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastResetTime);
        // cal.add(Calendar.DAY_OF_MONTH, 30);
        cal.add(Calendar.MINUTE, 1);
        Timestamp thirtyDaysLater = new Timestamp(cal.getTimeInMillis());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        return currentTime.after(thirtyDaysLater);
    }

    public Timestamp getLastResetTime() {
        return lastResetTime;
    }

    public List<Ticket> searchTicketByMoveName(String movieName) {
        if (movieName == null || movieName.isEmpty()) {
            return ticketService.getAllTickets();
        } else {
            return ticketRepository.findTicketsByMovieTitleContainingIgnoreCase(movieName);
        }
    }
}
