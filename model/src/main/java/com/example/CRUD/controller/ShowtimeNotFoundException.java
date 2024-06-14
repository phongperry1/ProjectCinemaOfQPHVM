package com.example.CRUD.controller;

public class ShowtimeNotFoundException extends Throwable {
    public ShowtimeNotFoundException(String message) {
        super(message);
    }
}