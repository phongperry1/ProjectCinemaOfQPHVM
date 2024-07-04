package com.example.CRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CRUD.Repository.FoodRepository;
import com.example.CRUD.controller.FoodNotFoundException;
import com.example.mo.Food;
import com.example.mo.FoodDTO;
import com.example.mo.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class FoodService {

    @Autowired
    private FoodRepository repo;
    
    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public void delete(Integer foodId) throws FoodNotFoundException {
        // Kiểm tra xem food có tồn tại hay không
        Food food = entityManager.find(Food.class, foodId);
        if (food == null) {
            throw new FoodNotFoundException("Food not found with ID: " + foodId);
        }

        // Xóa các hàng trong bảng ticket_food trước
        String deleteTicketFoodSql = "DELETE FROM ticket_food WHERE food_id = :foodId";
        entityManager.createNativeQuery(deleteTicketFoodSql)
            .setParameter("foodId", foodId)
            .executeUpdate();

        // Sau đó, xóa hàng trong bảng food
        entityManager.remove(food);
    }

    public List<FoodDTO> getFoodByCinemaOwnerId(int cinemaOwnerId) {
        return repo.getFoodByCinemaOwnerId(cinemaOwnerId);
    }
}
