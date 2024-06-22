package com.example.CRUD.Repository;


<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mo.CinemaOwner;

@Repository
public interface CinemaOwnerRepository extends JpaRepository<CinemaOwner, Integer> {
<<<<<<< HEAD
    List<CinemaOwner> findByCinemaNameContainingIgnoreCase(String cinemaName);
=======
    
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
}

