package com.example.CRUD.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.CRUD.Repository.FoodRepository;
import com.example.CRUD.Repository.ScreeningRoomRepository;
import com.example.CRUD.Repository.SeatRepository;
import com.example.CRUD.Repository.ShowtimeRepository;
import com.example.CRUD.Repository.TheaterRepository;
import com.example.CRUD.Repository.TicketRepository;
import com.example.CRUD.Repository.UserRepository;
import com.example.CRUD.service.FoodService;
import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.SeatService;
import com.example.CRUD.service.ShowtimeService;
import com.example.CRUD.service.TheaterService;
import com.example.CRUD.service.UserService;
import com.example.mo.Food;
import com.example.mo.Movie;
import com.example.mo.ScreeningRoom;
import com.example.mo.Seat;
import com.example.mo.SeatDTO;
import com.example.mo.Showtime;
import com.example.mo.ShowtimeDTO;
import com.example.mo.Theater;
import com.example.mo.Ticket;
import com.example.mo.TicketDTO;
import com.example.mo.Users;

import jakarta.mail.internet.ParseException;


@Controller
public class BookingController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService ;
    @Autowired
    private TheaterService theaterSer;
    @Autowired
    private ShowtimeService showtimeSer;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private ScreeningRoomRepository screeningRoomRepository;
    @Autowired
    private FoodService foodSer;


    @GetMapping("/booking/{userid}/{movieid}")
    public String showBookingPage(Model model, @PathVariable("movieid") Integer movieid, @PathVariable("userid") Integer userid) {
        Movie movie = movieService.getMovieById(movieid);
        List<Theater> theaters = theaterSer.listAll(); 
        List<Food> foods = foodSer.listAll();
        Users user = userService.getUserById(userid);
        model.addAttribute("user", user); 
        model.addAttribute("theaters", theaters); 
        model.addAttribute("movie", movie);
        model.addAttribute("foods", foods);
        model.addAttribute("movieID", movie.getMovieID());
        return "booking"; 
    }

    @GetMapping("/showtimes/getShowtimes")
    @ResponseBody
    public List<ShowtimeDTO> getShowtimesByMovieIDAndTheaterID(@RequestParam("movieID") Integer movieID, @RequestParam("theaterID") Integer theaterID) {
        List<Object[]> results = showtimeSer.getShowtimesByMovieIDAndTheaterID(movieID, theaterID);
        List<ShowtimeDTO> showtimes = new ArrayList<>();
        for (Object[] result : results) {
            Date showDate = (Date) result[0];
            Time showTime = (Time) result[1];
            Integer showtimeId = (Integer) result[2];
            showtimes.add(new ShowtimeDTO(showtimeId, showDate, showTime));
        }
    return showtimes;
    }

    @GetMapping("/showtimes/getRoomName")
    @ResponseBody
    public List<ScreeningRoom> getRoomName(@RequestParam int theaterID, 
                                         @RequestParam int movieID, 
                                         @RequestParam Time showTime,
                                         @RequestParam Date showDate) {
        List<ScreeningRoom> screeningroom = screeningRoomRepository.findScreeningRoomsByTheaterIdAndMovieIdAndShowTimeAndShowDate(theaterID, movieID, showTime, showDate);               
        return screeningroom;
    }
    
    @PostMapping("/tickets/create")
    public Ticket updateSeatStatus(@RequestBody TicketDTO ticketDTO) throws ParseException {

            int userId = parseId(ticketDTO.getUserId(), "userId");
            int movieId = parseId(ticketDTO.getMovieId(), "movieId");
            int theaterId = Integer.parseInt(ticketDTO.getTheaterId());
            int showtimeId = Integer.parseInt(ticketDTO.getShowtimeId());

            Movie movie = movieService.getMovieById(movieId);
            Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Theater not found"));
            Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(() -> new RuntimeException("Showtime not found"));

            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setMovie(movie);
            ticket.setTheater(theater);
            ticket.setShowtime(showtime);
            ticket.setPrice(ticketDTO.getTotalPrice3());


            Ticket savedTicket = ticketRepository.save(ticket);

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

            List<Food> selectedFoods = new ArrayList<>();
            for (String foodDetail : ticketDTO.getSelectedFood()) {
                String[] details = foodDetail.split(" id");
                if (details.length > 1) {
                    int foodId = Integer.parseInt(details[details.length - 1]);
                    Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found"));
                    selectedFoods.add(food);
                }
            }
            ticket.setFoods(selectedFoods);
            ticketRepository.save(ticket);

            return savedTicket;
    }
            private int parseId(String id, String fieldName) {
                if (id == null || id.isEmpty()) {
                    throw new IllegalArgumentException(fieldName + " không thể null hoặc trống");
                }
                return Integer.parseInt(id);
            }

    

    @GetMapping("/seats/booked")
    @ResponseBody
    public List<SeatDTO> getBookedSeats(@RequestParam Integer screeningRoomId,
                                        @RequestParam Integer showtimeId) {
        List<Object[]> rusults = seatService.getBookedSeats(screeningRoomId, showtimeId);
        List<SeatDTO> bookedSeats = new ArrayList<>();
        for(Object[] result : rusults) {
            String seatName= (String) result[0];
            String seatType = (String) result[1];
            boolean statusSeat = (boolean) result[2];
            bookedSeats.add( new SeatDTO(seatName, seatType, statusSeat));
        }
        return bookedSeats;
    }

    @GetMapping("/foods")
    @ResponseBody
    public List<Food> getAllFoods() {
        return foodSer.listAll();
    }


    
}

