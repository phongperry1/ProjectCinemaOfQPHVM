package com.example.CRUD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.CinemaOwnerRepository;
import com.example.mo.CinemaOwner;


@Service
public class CinemaService {
    @Autowired
    private CinemaOwnerRepository cinemaOwnerRepository;
    public List<CinemaOwner> getCinemaOwnerByAdmins(){
        return cinemaOwnerRepository.findAll();
    }

	public List<CinemaOwner> searchCinemasOwnerByCinemaName(String cinemaName) {
        if (cinemaName == null || cinemaName.isEmpty()) {
            return cinemaOwnerRepository.findAll();
        } else {
            return cinemaOwnerRepository.findByCinemaNameContainingIgnoreCase(cinemaName);
        }
    }
}