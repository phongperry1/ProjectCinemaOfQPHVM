package com.example;



import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.CRUD.Repository.FoodRepository;
import com.example.mo.Food;







@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class FoodRepositoryTest {
    @Autowired private FoodRepository repo;

     @Test
    public void testAddNew() {
        Food food = new Food();
        food.setCinemaOwnerID(2);;
        food.setFoodName("Coca");
        food.setPrice(37000);
        food.setPhotoFood("442510283_960766749180483_1676810938603420038_n.jpg");
        Food savedFood = repo.save(food);

        Assertions.assertThat(savedFood).isNotNull();
        Assertions.assertThat(savedFood.getFoodID()).isGreaterThan(0);

    }

     @Test 
    public void testListAll() {
       Iterable<Food> foods = repo.findAll();
       Assertions.assertThat(foods).hasSizeGreaterThan(0);

       for (Food food : foods) {
            System.out.println(food);
       }
    }

     @Test
    public void testUpdate() {
        Integer foodId = 2;
        Optional<Food> optionalFood = repo.findById(foodId);
        Food food = optionalFood.get();
        food.setFoodName("Pepsi");;
        repo.save(food);

        Food updatedFood = repo.findById(foodId).get();
        Assertions.assertThat(updatedFood.getFoodName()).isEqualTo("Pepsi");
    }

     @Test
    public void testGet() {
        Integer foodId = 2;
        Optional<Food> optionalFood = repo.findById(foodId);
        Assertions.assertThat(optionalFood).isPresent();
        System.out.println(optionalFood.get());
    }

     @Test
    public void testDelete() {
        Integer foodId = 2;
        repo.deleteById(foodId);
        Optional<Food> optionalFood = repo.findById(foodId);
        Assertions.assertThat(optionalFood).isNotPresent();


    }













}