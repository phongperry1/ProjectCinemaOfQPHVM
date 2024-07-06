package com.example.CRUD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.TheaterRepository;
import com.example.CRUD.controller.TheaterNotFoundException;
import com.example.mo.Theater;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository repo;

    public List<Theater> listAll() {
        return repo.findAll();
    }

    public List<Theater> listAllByCinemaOwnerID(Integer cinemaOwnerID) {
        return repo.findByCinemaOwnerID(cinemaOwnerID);
    }

    public Theater save(Theater theater) {
        return repo.save(theater);
    }

    public Theater get(Integer theaterID) throws TheaterNotFoundException {
        return repo.findById(theaterID).orElseThrow(() -> new TheaterNotFoundException("Theater not found"));
    }

    public void delete(Integer theaterID) throws TheaterNotFoundException {
        if (!repo.existsById(theaterID)) {
            throw new TheaterNotFoundException("Theater not found");
        }
        repo.deleteById(theaterID);
    }
}
