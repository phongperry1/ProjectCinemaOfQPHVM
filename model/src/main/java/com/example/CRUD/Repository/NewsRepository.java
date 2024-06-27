package com.example.CRUD.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mo.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByCinemaOwnerID(Integer cinemaOwnerID);
}
