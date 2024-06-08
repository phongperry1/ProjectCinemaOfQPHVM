package com.example.CRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.ScreeningRoomRepository;
import com.example.CRUD.controller.ScreeningRoomNotFoundException;
import com.example.mo.ScreeningRoom;




@Service
public class ScreeningRoomService {
    @Autowired
    private ScreeningRoomRepository repo;

     public List<ScreeningRoom> listAll() {
        return (List<ScreeningRoom>) repo.findAll();
    }

     public void save(ScreeningRoom screeningroom) {
        repo.save(screeningroom);
    }

     public ScreeningRoom get(Integer screeningroomID) throws ScreeningRoomNotFoundException {
        Optional<ScreeningRoom> result = repo.findById(screeningroomID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ScreeningRoomNotFoundException("Could not find any Screeningroom with ID " + screeningroomID);
    }


     public void delete(Integer ScreeningRoomID) throws ScreeningRoomNotFoundException {
        if (!repo.existsById(ScreeningRoomID)) {
            throw new ScreeningRoomNotFoundException("Could not find any theater with ID " + ScreeningRoomID);
        }
        repo.deleteById(ScreeningRoomID);
    } 

}