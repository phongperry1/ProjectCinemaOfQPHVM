package com.example;




import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.CRUD.Repository.ScreeningRoomRepository;
import com.example.mo.ScreeningRoom;







@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ScreeningRoomRepositoryTest {
    @Autowired private ScreeningRoomRepository repo;

    @Test
    public void testAddNew() {
        ScreeningRoom screeningRoom = new ScreeningRoom();
        
        screeningRoom.setRoomname("VIP");
        ScreeningRoom savedScreeningRoom = repo.save(screeningRoom);

        Assertions.assertThat(savedScreeningRoom).isNotNull();
        Assertions.assertThat(savedScreeningRoom.getScreeningRoomID()).isGreaterThan(0);

    }

    @Test 
    public void testListAll() {
       Iterable<ScreeningRoom> screeningRooms = repo.findAll();
       Assertions.assertThat(screeningRooms).hasSizeGreaterThan(0);

       for (ScreeningRoom screeningRoom : screeningRooms) {
            System.out.println(screeningRoom);
       }
    }


    @Test
    public void testUpdate() {
        Integer screeningRoomId = 3;
        Optional<ScreeningRoom> optionalScreeningRoom = repo.findById(screeningRoomId);
        ScreeningRoom screeningRoom = optionalScreeningRoom.get();
        screeningRoom.setRoomname("VIP");;
        repo.save(screeningRoom);

        ScreeningRoom updatedScreeningRoom = repo.findById(screeningRoomId).get();
        Assertions.assertThat(updatedScreeningRoom.getRoomname()).isEqualTo("VIP");
    }
    
    @Test
    public void testGet() {
        Integer screeningRoomId = 3;
        Optional<ScreeningRoom> optionalScreeningRoom = repo.findById(screeningRoomId);
        Assertions.assertThat(optionalScreeningRoom).isPresent();
        System.out.println(optionalScreeningRoom.get());
    }

     @Test
    public void testDelete() {
        Integer screeningRoomId = 3;
        repo.deleteById(screeningRoomId);
        Optional<ScreeningRoom> optionalScreeningRoom = repo.findById(screeningRoomId);
        Assertions.assertThat(optionalScreeningRoom).isNotPresent();


    }






}