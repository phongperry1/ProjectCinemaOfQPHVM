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

    public Theater save(Theater theater) {
        return repo.save(theater);
    }

    public Theater get(Integer id) throws TheaterNotFoundException {
        return repo.findById(id).orElseThrow(() -> new TheaterNotFoundException("Could not find any theaters with ID " + id));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
