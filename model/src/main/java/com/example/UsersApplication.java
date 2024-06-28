// package com.example;

// import java.sql.Date;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.ApplicationContext;

// import com.example.CRUD.Repository.TicketRepository;
// import com.example.CRUD.Repository.UserRepository;
// import com.example.CRUD.service.MovieService;
// import com.example.CRUD.service.UserService;
// import com.example.mo.Movie;
// import com.example.mo.Ticket;
// import com.example.mo.TicketDTO;
// import com.example.mo.Users;

// @SpringBootApplication
// public class UsersApplication {
// 	@Autowired
//     private MovieService movieService;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private TicketRepository ticketRepository;
// 	public static void main(String[] args) {
// 		ApplicationContext context = SpringApplication.run(UsersApplication.class, args);
// 	// 	MovieService movieService = context.getBean(MovieService.class);
// 	// 	UserRepository userRepository = context.getBean(UserRepository.class);
// 	// 	TicketRepository ticketRepository = context.getBean(TicketRepository.class);


// 	// 	int userid = 1; // Example user ID
//     //     int movieid = 1; // Example movie ID
//     //     TicketDTO ticketDTO = new TicketDTO(); // Populate this DTO with necessary data
//     //     ticketDTO.setTheaterID(1);
//     //     ticketDTO.setShowtimeid(1);
//     //     ticketDTO.setShowdate(Date.valueOf("2024-07-08"));
//     //     ticketDTO.setTotalPrice3(15.99);

//     //     System.out.println("User ID: " + userid);
//     //     System.out.println("Movie ID: " + movieid);

//     //     Movie movie = movieService.getMovieById(movieid);
//     //     Users user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
//     //     Ticket ticket = new Ticket();

//     //     ticket.setUser(user);
//     //     ticket.setMovie(movie);
//     //     ticket.setTheaterId(ticketDTO.getTheaterID());
//     //     ticket.setShowtimeId(ticketDTO.getShowtimeid());
//     //     ticket.setShowDate(ticketDTO.getShowdate());
//     //     ticket.setPrice(ticketDTO.getTotalPrice3());

//     //     // Save the ticket to the database
//     //     ticketRepository.save(ticket);

//     //     System.out.println("Ticket saved successfully!");
//     // }

			
// 	}


