package com.example.CRUD.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.ScreeningRoomRepository;

import com.example.mo.ScreeningRoom;

@Service
public class ScreeningRoomServiceImpl {
    private final ScreeningRoomRepository screeningRoomRepository;
    public ScreeningRoomServiceImpl(ScreeningRoomRepository screeningRoomRepository) {
        this.screeningRoomRepository = screeningRoomRepository;
    }

     public void printScreenRoom() {
        List<ScreeningRoom> screeningRooms = screeningRoomRepository.findAll();
        if(screeningRooms.isEmpty()) {
            System.out.println("No user found in the database.");
        } else {
            screeningRooms.forEach(System.out::println);
        }
    }
}