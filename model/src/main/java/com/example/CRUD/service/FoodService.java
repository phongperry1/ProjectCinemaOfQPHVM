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

<<<<<<< HEAD

=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
     public List<Food> listAll() {
        return (List<Food>) repo.findAll();
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

    public void delete(Integer FoodID) throws FoodNotFoundException {
        if (!repo.existsById(FoodID)) {
            throw new FoodNotFoundException("Could not find any food with ID " + FoodID);
        }
        repo.deleteById(FoodID);
    } 


}