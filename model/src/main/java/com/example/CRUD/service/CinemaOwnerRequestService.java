package com.example.CRUD.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.CinemaOwnerRepository;
import com.example.CRUD.Repository.CinemaOwnerRequestRepository;
import com.example.CRUD.Repository.UserRepository;
import com.example.mo.CinemaOwner;
import com.example.mo.CinemaOwnerRequest;
import com.example.mo.Users;

@Service
public class CinemaOwnerRequestService {
    private CinemaOwnerRequestRepository cinemaOwnerRequestRepository;
    private CinemaOwnerRepository cinemaOwnerRepository;
    private UserRepository userRepository;

    @Autowired
    public void setCinemaOwnerRequestRepository(CinemaOwnerRequestRepository cinemaOwnerRequestRepository, UserRepository userRepository) {
        this.cinemaOwnerRequestRepository = cinemaOwnerRequestRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCinemaOwnerRepository(CinemaOwnerRepository cinemaOwnerRepository) {
        this.cinemaOwnerRepository = cinemaOwnerRepository;
    }

    public CinemaOwnerRequest saveCinemaOwnerRequest(CinemaOwnerRequest cinemaOwnerRequest) {
        return cinemaOwnerRequestRepository.save(cinemaOwnerRequest);
    }

    public List<CinemaOwnerRequest> getAllCinemaOwnerRequests() {
        return cinemaOwnerRequestRepository.findAll();
    }

    public void approveCinemaOwnerRequest(int cinemaOwnerRequestId) {
        CinemaOwnerRequest request = cinemaOwnerRequestRepository.findById(cinemaOwnerRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Cinema owner request not found"));
        Users user = request.getUsers();
        user.setRole("ROLE_CINEMA_OWNER");
        userRepository.save(user);
        CinemaOwner cinemaOwner = new CinemaOwner();
        cinemaOwner.setUsers(request.getUsers());
        cinemaOwner.setCinemaName(request.getCinemaName());
        cinemaOwner.setAddressCinema(request.getAddressCinema());
        cinemaOwner.setHotline(request.getHotline());
        cinemaOwner.setEmail(request.getEmail());
        cinemaOwner.setEmployeeID(request.getEmployeeID());

        cinemaOwnerRepository.save(cinemaOwner);
        cinemaOwnerRequestRepository.delete(request);
    }

    public void rejectCinemaOwnerRequest(int cinemaOwnerRequestId) {
        cinemaOwnerRequestRepository.deleteById(cinemaOwnerRequestId);
    }

    public boolean isUserRegisteredAsCinemaOwner(int userId) {
        CinemaOwnerRequest cinemaOwnerRequest = cinemaOwnerRequestRepository.findByUsersUserId(userId);
        return cinemaOwnerRequest != null;
    }
    
}