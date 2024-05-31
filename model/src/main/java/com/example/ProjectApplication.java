package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.CRUD.service.CinemaOwnerServiceImpl;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

	@Autowired
	private CinemaOwnerServiceImpl cinemaService;
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cinemaService.printCinema();
	}

}
