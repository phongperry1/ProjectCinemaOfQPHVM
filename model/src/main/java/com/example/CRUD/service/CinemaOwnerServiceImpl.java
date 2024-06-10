package com.example.CRUD.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.CinemaOwnerRepository;
import com.example.mo.CinemaOwner;

@Service
public class CinemaOwnerServiceImpl {

    private final CinemaOwnerRepository cinemaOwnerRepository;
    public CinemaOwnerServiceImpl(CinemaOwnerRepository cinemaOwnerRepository) {
        this.cinemaOwnerRepository = cinemaOwnerRepository;
    }

    

    public void printCinema() {
        List<CinemaOwner> cinema = cinemaOwnerRepository.findAll();
        if(cinema.isEmpty()) {
            System.out.println("No user found in the database.");
        } else {
            cinema.forEach(System.out::println);
        }
    }
}