package com.example.CRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.NewsRepository;
import com.example.CRUD.controller.NewsNotFoundException;
import com.example.mo.News;

@Service
public class NewsService {
    @Autowired
    private NewsRepository repo;

    public List<News> listAll() {
        return repo.findAll();
    }

    public List<News> listAllByCinemaOwnerID(Integer cinemaOwnerID) {
        return repo.findByCinemaOwnerID(cinemaOwnerID);
    }

    public News save(News news) {
        return repo.save(news);
    }

    public News get(Integer newsID) throws NewsNotFoundException {
        Optional<News> result = repo.findById(newsID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new NewsNotFoundException("Could not find any news with ID " + newsID);
    }

    public void delete(Integer newsID) throws NewsNotFoundException {
        if (!repo.existsById(newsID)) {
            throw new NewsNotFoundException("Could not find any news with ID " + newsID);
        }
        repo.deleteById(newsID);
    }
    public List<News> getAllNews() {
        // Implement logic to fetch all news items from repository or database
        // Example:
        return repo.findAll(); // Assuming newsRepository is an instance of JpaRepository or similar
    }
}
