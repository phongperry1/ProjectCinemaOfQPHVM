package com.example.CRUD.controller;

public class NewsNotFoundException extends Throwable {
    public NewsNotFoundException(String message) {
        super(message);
    }
}