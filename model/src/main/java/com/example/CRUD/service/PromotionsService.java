package com.example.CRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.PromotionsRepository;
import com.example.CRUD.controller.PromotionsNotFoundException;
import com.example.mo.Promotions;



@Service
public class PromotionsService {
     @Autowired
    private PromotionsRepository repo;

     public List<Promotions> listAll() {
        return (List<Promotions>) repo.findAll();
    }

     public Promotions save(Promotions promotions) {
        return repo.save(promotions);
    }

     public Promotions get(Integer promotionsID) throws PromotionsNotFoundException {
        Optional<Promotions> result = repo.findById(promotionsID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new PromotionsNotFoundException("Could not find any promotions with ID " + promotionsID);
    }


     public void delete(Integer PromotionID) throws PromotionsNotFoundException {
        if (!repo.existsById(PromotionID)) {
            throw new PromotionsNotFoundException("Could not find any promotions with ID " + PromotionID);
        }
        repo.deleteById(PromotionID);
    } 



}