package com.example.CRUD.service;

import com.example.CRUD.Repository.*;
import com.example.mo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FoodRepository foodRepository;

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
        revenueByMovie.forEach((movie, revenue) -> logger.info("Movie: " + movie + ", Revenue: " + revenue));

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

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        return ticketRepository.findByUserUserId(userId);
    }

    @Transactional
    public void deleteTicketById(int ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Transactional
    public void savePendingTicket(TicketDTO ticketDTO, Users user) {
        try {
            logger.info("Saving ticket: " + ticketDTO);
            int movieId = Integer.parseInt(ticketDTO.getMovieId());
            int theaterId = Integer.parseInt(ticketDTO.getTheaterId());
            int showtimeId = Integer.parseInt(ticketDTO.getShowtimeId());

            Movie movie = movieService.getMovieById(movieId);
            Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Theater not found"));
            Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(() -> new RuntimeException("Showtime not found"));

            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setMovie(movie);
            ticket.setTheater(theater);
            ticket.setShowtime(showtime);
            ticket.setPrice(ticketDTO.getTotalPrice3());
            ticket.setOrderInfo(ticketDTO.getOrderInfo());
            ticket.setShowDate(ticketDTO.getShowdate());

            Ticket savedTicket = ticketRepository.save(ticket);
            logger.info("Ticket saved with ID: " + savedTicket.getTicketId());

            List<Seat> seats = new ArrayList<>();
            for (Seat seatDTO : ticketDTO.getSelectedSeats()) {
                Seat seat = new Seat();
                seat.setSeatName(seatDTO.getSeatName());
                seat.setSeatType(seatDTO.getSeatType());
                seat.setStatusSeat(true);
                seat.setScreeningRoomId(seatDTO.getScreeningRoomId());
                seat.setShowtimeId(seatDTO.getShowtimeId());
                seat.setTicket(savedTicket);
                seats.add(seat);
            }
            seatRepository.saveAll(seats);

            if (ticketDTO.getSelectedFood() != null) {
                List<Food> selectedFoods = new ArrayList<>();
                for (String foodDetail : ticketDTO.getSelectedFood()) {
                    String[] details = foodDetail.split(" id");
                    if (details.length > 1) {
                        int foodId = Integer.parseInt(details[1].trim());
                        Food food = foodRepository.findById(foodId)
                                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
                        selectedFoods.add(food);
                    }
                }
                ticket.setFoods(selectedFoods);
            }

            ticketRepository.save(ticket);
            logger.info("Ticket fully saved with seats and foods.");
        } catch (Exception e) {
            logger.error("Error saving ticket: " + e.getMessage(), e);
            throw new RuntimeException("Error saving ticket: " + e.getMessage());
        }
    }

    public Ticket getTicketById(int ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));
    }
}
