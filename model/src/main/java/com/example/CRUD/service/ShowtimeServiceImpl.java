package com.example.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.ShowtimeRepository;

import com.example.mo.Showtime;

@Service
public class ShowtimeServiceImpl {
    private final ShowtimeRepository showtimeRepository;
    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

     public void printShowtime() {
        List<Showtime> showtimes = showtimeRepository.findAll();
        if(showtimes.isEmpty()) {
            System.out.println("No user found in the database.");
        } else {
            showtimes.forEach(System.out::println);
        }
    }
}