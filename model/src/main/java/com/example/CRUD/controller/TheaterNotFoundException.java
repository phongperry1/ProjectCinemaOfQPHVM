package com.example.CRUD.controller;

public class TheaterNotFoundException extends Throwable {
    public TheaterNotFoundException(String message) {
        super(message);
    }
}