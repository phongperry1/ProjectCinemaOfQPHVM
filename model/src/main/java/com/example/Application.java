package com.example;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.CRUD.service.TicketService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Retrieve beans after the application context has been initialized
        TicketService ticketService = context.getBean(TicketService.class);

        // Use the services provided by the beans
        Map<String, Double> revenueByMovie = ticketService.calculateRevenueByMovie();
        revenueByMovie.forEach((movieTitle, revenue) -> {
            System.out.println("Movie: " + movieTitle + ", Revenue: " + revenue);
        });
    }
}
