package com.example.CRUD.Repository;

import com.example.mo.CinemaOwnerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaOwnerTransactionRepository extends JpaRepository<CinemaOwnerTransaction, Integer> {
    List<CinemaOwnerTransaction> findByCinemaOwnerCinemaOwnerID(int cinemaOwnerID);
}
