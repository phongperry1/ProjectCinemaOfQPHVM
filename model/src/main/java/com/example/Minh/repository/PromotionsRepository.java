package com.example.Minh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Minh.model.Promotions;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer> {
    
}