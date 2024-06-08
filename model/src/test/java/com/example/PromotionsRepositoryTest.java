package com.example;

import java.sql.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import com.example.CRUD.Repository.PromotionsRepository;
import com.example.mo.Promotions;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PromotionsRepositoryTest {
    @Autowired private PromotionsRepository repo;

    @Test
    public void testAddNew() {
        Promotions promotions = new Promotions();
        promotions.setCinemaOwnerID(2);
        promotions.setMovieID(1);
        promotions.setPromotionName("Khuyen mai tang ly");
        promotions.setDescription("Tang sticker Conan");
        promotions.setDiscountRate(50.5);
        promotions.setStartDate(Date.valueOf("2024-05-30"));
        promotions.setEndDate(Date.valueOf("2024-06-20"));
        
       
        Promotions savedPromotions = repo.save(promotions);

        Assertions.assertThat(savedPromotions).isNotNull();
        Assertions.assertThat(savedPromotions.getPromotionID()).isGreaterThan(0);

    }

    @Test 
    public void testListAll() {
       Iterable<Promotions> promotions = repo.findAll();
       Assertions.assertThat(promotions).hasSizeGreaterThan(0);

       for (Promotions promotion : promotions) {
            System.out.println(promotion);
       }
    }

      @Test
    public void testUpdate() {
        Integer promotionsId = 1;
        Optional<Promotions> optionalPromotions = repo.findById(promotionsId);
        Promotions promotions = optionalPromotions.get();
        promotions.setDescription("Tang Sticker gojo");;
        repo.save(promotions);

        Promotions updatedPromotions = repo.findById(promotionsId).get();
        Assertions.assertThat(updatedPromotions.getDescription()).isEqualTo("Tang Sticker gojo");
    }

     @Test
    public void testGet() {
        Integer promotionsId = 1;
        Optional<Promotions> optionalPromotions = repo.findById(promotionsId);
        Assertions.assertThat(optionalPromotions).isPresent();
        System.out.println(optionalPromotions.get());
    }

    @Test
    public void testDelete() {
        Integer promotionsId = 2;
        repo.deleteById(promotionsId);
        Optional<Promotions> optionalPromotions = repo.findById(promotionsId);
        Assertions.assertThat(optionalPromotions).isNotPresent();


    }









}