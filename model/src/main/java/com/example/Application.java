package com.example;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.mo.Promotions;
import com.example.CRUD.Repository.PromotionsRepository;
import com.example.CRUD.service.PromotionsService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Retrieve beans after the application context has been initialized
        PromotionsRepository promotionsRepository = context.getBean(PromotionsRepository.class);
        PromotionsService promotionsService = context.getBean(PromotionsService.class);

        // Use the services provided by the beans
        List<Promotions> promotions = promotionsService.listAll();
        for (Promotions promotions2 : promotions) {
            System.out.println(promotions2);
        }
    }
}
