package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mo.Food;
import java.util.List;


@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>{

}