package com.example;

import java.util.Optional;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.CRUD.Repository.TheaterRepository;
import com.example.mo.Theater;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TheaterRepositoryTest {
    @Autowired private TheaterRepository repo;

    @Test
    public void testAddNew() {
        Theater theater = new Theater();
        theater.setMap("Da Nang");
        theater.setTheaterName("MOMOMOMO");
        theater.setCinemaOwnerID(100);
        Theater savedTheater = repo.save(theater);

        Assertions.assertThat(savedTheater).isNotNull();
        Assertions.assertThat(savedTheater.getTheaterID()).isGreaterThan(0);

    }

    @Test 
    public void testListAll() {
       Iterable<Theater> theaters = repo.findAll();
       Assertions.assertThat(theaters).hasSizeGreaterThan(0);

       for (Theater theater : theaters) {
            System.out.println(theater);
       }
    }

    @Test
    public void testUpdate() {
        Integer theaterrId = 1;
        Optional<Theater> optionalTheater = repo.findById(theaterrId);
        Theater theater = optionalTheater.get();
        theater.setMap("Vung Tau");
        repo.save(theater);

        Theater updatedTheater = repo.findById(theaterrId).get();
        Assertions.assertThat(updatedTheater.getMap()).isEqualTo("Vung Tau");
    }

    @Test
    public void testGet() {
        Integer theaterrId = 2;
        Optional<Theater> optionalTheater = repo.findById(theaterrId);
        Assertions.assertThat(optionalTheater).isPresent();
        System.out.println(optionalTheater.get());
    }

    @Test
    public void testDelete() {
        Integer theaterrId = 3;
        repo.deleteById(theaterrId);
        Optional<Theater> optionalTheater = repo.findById(theaterrId);
        Assertions.assertThat(optionalTheater).isNotPresent();


    }

}