package com.example.CRUD.Repository;

import com.example.mo.Rating;
import com.example.mo.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByMovie_MovieID(Integer movieID);
    Rating findByUserAndMovie_MovieID(Users user, Integer movieId);
}