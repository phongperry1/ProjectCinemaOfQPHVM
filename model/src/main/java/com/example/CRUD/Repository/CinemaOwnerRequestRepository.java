package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mo.CinemaOwnerRequest;

@Repository
public interface CinemaOwnerRequestRepository extends JpaRepository<CinemaOwnerRequest, Integer> {
    CinemaOwnerRequest findByUsersUserId(int userId);
}