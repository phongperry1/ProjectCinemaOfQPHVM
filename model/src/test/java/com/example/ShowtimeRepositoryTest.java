package com.example;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.CRUD.Repository.ShowtimeRepository;
import com.example.mo.Showtime;







@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ShowtimeRepositoryTest {
    @Autowired private ShowtimeRepository repo;

    @Test
    public void testAddNew() {
        Showtime showtime = new Showtime();
        showtime.setShowDate(Date.valueOf("2024-06-02"));  
        showtime.setShowTime(Time.valueOf("18:30:00"));
        // showtime.setMovieID(1);    
        showtime.setScreeningRoomID(11);
        Showtime savedShowtime = repo.save(showtime);

        Assertions.assertThat(savedShowtime).isNotNull();
        Assertions.assertThat(savedShowtime.getShowtimeID()).isGreaterThan(0);

    }

    @Test 
    public void testListAll() {
       Iterable<Showtime> showtimes = repo.findAll();
       Assertions.assertThat(showtimes).hasSizeGreaterThan(0);

       for (Showtime showtime : showtimes) {
            System.out.println(showtime);
       }
    }

     @Test
    public void testUpdate() {
        Integer showTimeId = 4;
        Optional<Showtime> optionalShowtime = repo.findById(showTimeId);
        Showtime showtime = optionalShowtime.get();
        showtime.setShowDate(Date.valueOf("2024-11-21"));;
        repo.save(showtime);

        Showtime updatedShowtime = repo.findById(showTimeId).get();
        Assertions.assertThat(updatedShowtime.getShowDate()).isEqualTo(Date.valueOf("2024-11-21"));
    }


    @Test
    public void testGet() {
        Integer showTimeId = 3;
        Optional<Showtime> optionalShowtime = repo.findById(showTimeId);
        Assertions.assertThat(optionalShowtime).isPresent();
        System.out.println(optionalShowtime.get());
    }


    @Test
    public void testDelete() {
        Integer showTimeId = 3;
        repo.deleteById(showTimeId);
        Optional<Showtime> optionalShowtime = repo.findById(showTimeId);
        Assertions.assertThat(optionalShowtime).isNotPresent();


    }





    
}