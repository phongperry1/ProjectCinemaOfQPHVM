package com.example.CRUD.service;

import com.example.CRUD.Repository.TicketRepository;
import com.example.mo.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Map<String, Double> calculateRevenueByMovie() {
        List<Ticket> allTickets = ticketRepository.findAll();
        Map<String, Double> revenueByMovie = new HashMap<>();

        for (Ticket ticket : allTickets) {
            if (ticket.getMovie() != null && ticket.getMovie().getTitle() != null) {
                String movieTitle = ticket.getMovie().getTitle();
                double price = ticket.getPrice();

                revenueByMovie.put(movieTitle, revenueByMovie.getOrDefault(movieTitle, 0.0) + price);
            }
        }

        // Log the calculated revenue for debugging purposes
        revenueByMovie.forEach((movie, revenue) -> System.out.println("Movie: " + movie + ", Revenue: " + revenue));

        return revenueByMovie;
    }

    public Map<String, Map<LocalDate, Double>> calculateRevenueByDay() {
        List<Ticket> allTickets = ticketRepository.findAll();
        Map<String, Map<LocalDate, Double>> revenueByDay = new HashMap<>();

        for (Ticket ticket : allTickets) {
            if (ticket.getShowDate() != null && ticket.getMovie() != null) {
                LocalDate date = ticket.getShowDate().toLocalDate();
                String movieTitle = ticket.getMovie().getTitle();
                double price = ticket.getPrice();

                revenueByDay.computeIfAbsent(movieTitle, k -> new HashMap<>())
                            .merge(date, price, Double::sum);
            }
        }
        return revenueByDay;
    }

    public Map<String, Map<String, Double>> calculateRevenueByMonth() {
        List<Ticket> allTickets = ticketRepository.findAll();
        Map<String, Map<String, Double>> revenueByMonth = new HashMap<>();

        for (Ticket ticket : allTickets) {
            if (ticket.getShowDate() != null && ticket.getMovie() != null) {
                LocalDate date = ticket.getShowDate().toLocalDate();
                String movieTitle = ticket.getMovie().getTitle();
                String monthYear = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + date.getYear();
                double price = ticket.getPrice();

                revenueByMonth.computeIfAbsent(movieTitle, k -> new HashMap<>())
                              .merge(monthYear, price, Double::sum);
            }
        }
        return revenueByMonth;
    }
}
