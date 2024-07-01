package com.example.CRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.TheaterRepository;
import com.example.mo.Theater;
import com.example.CRUD.controller.TheaterNotFoundException;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository repo;


    public List<Theater> listAll() {
        return (List<Theater>) repo.findAll();
    }

    public Theater save(Theater theater) {
        return repo.save(theater);
    }

    public Theater get(Integer theaterID) throws TheaterNotFoundException {
        Optional<Theater> result = repo.findById(theaterID);
        if (result.isPresent()) {
            return result.get();
        }   
        throw new TheaterNotFoundException("Could not find any theater with ID " + theaterID);
    }

    public void delete(Integer TheaterID) throws TheaterNotFoundException {
        if (!repo.existsById(TheaterID)) {
            throw new TheaterNotFoundException("Could not find any theater with ID " + TheaterID);
        }
        repo.deleteById(TheaterID);
    } 
}
