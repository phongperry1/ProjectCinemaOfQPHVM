package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.mo.Promotions;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer> {
    List<Promotions> findByCinemaOwnerID(Integer cinemaOwnerID);
}
