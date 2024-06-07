package com.example.CRUD.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mo.CinemaOwner;

@Repository
public interface CinemaOwnerRepository extends JpaRepository<CinemaOwner, Integer> {
    
}

