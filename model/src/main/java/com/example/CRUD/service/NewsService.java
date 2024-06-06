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
        return (List<News>) repo.findAll();
    }

     public void save(News news) {
        repo.save(news);
    }

    public News get(Integer newsID) throws NewsNotFoundException {
        Optional<News> result = repo.findById(newsID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new NewsNotFoundException("Could not find any news with ID " + newsID);
    }

    public void delete(Integer NewsID) throws NewsNotFoundException {
        if (!repo.existsById(NewsID)) {
            throw new NewsNotFoundException("Could not find any news with ID " + NewsID);
        }
        repo.deleteById(NewsID);
    } 
}