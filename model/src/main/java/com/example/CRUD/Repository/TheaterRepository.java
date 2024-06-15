package com.example.CRUD.Repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mo.Theater;

@Repository
public interface TheaterRepository extends JpaRepository <Theater ,Integer>{
    
    Theater findByTheaterName(String theaterName);

    

    
}