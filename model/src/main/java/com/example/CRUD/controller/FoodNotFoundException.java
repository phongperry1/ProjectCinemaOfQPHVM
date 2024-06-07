package com.example.CRUD.controller;

public class FoodNotFoundException extends Throwable {
    public FoodNotFoundException(String message) {
       super(message);
    }
}