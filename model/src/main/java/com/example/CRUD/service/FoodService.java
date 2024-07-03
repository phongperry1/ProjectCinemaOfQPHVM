package com.example.CRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.FoodRepository;
import com.example.CRUD.controller.FoodNotFoundException;
import com.example.mo.Food;

@Service
public class FoodService {
    @Autowired
    private FoodRepository repo;

    public List<Food> listAllByCinemaOwnerID(Integer cinemaOwnerID) {
        return repo.findByCinemaOwnerID(cinemaOwnerID);
    }

    public List<Food> listAll() {
        return repo.findAll();
    }

    public Food save(Food food) {
        return repo.save(food);
    }

    public Food get(Integer foodID) throws FoodNotFoundException {
        Optional<Food> result = repo.findById(foodID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new FoodNotFoundException("Could not find any food with ID " + foodID);
    }

    public void delete(Integer foodID) throws FoodNotFoundException {
        if (!repo.existsById(foodID)) {
            throw new FoodNotFoundException("Could not find any food with ID " + foodID);
        }
        repo.deleteById(foodID);
    }

    public List<Food> getFoodByCinemaOwnerId(int cinemaOwnerId) {
        return repo.getFoodByCinemaOwnerId(cinemaOwnerId);
    }
}
