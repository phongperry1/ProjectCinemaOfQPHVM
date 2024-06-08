package com.example;

import java.sql.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.CRUD.Repository.NewsRepository;
import com.example.mo.News;








@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class NewsRepositoryTest {
    @Autowired private NewsRepository repo;

    @Test
    public void testAddNew() {
        News news = new News();
        news.setAuthorID(2);
        news.setCinemaOwnerID(1);
        news.setMovieID(1);
        news.setContent("Tin tuc hom nay rat la hot luon");
        news.setPublishDate(Date.valueOf("2024-06-26"));
        news.setTitle("Khuyen mai hot tai rap CGV nha");
        News savedNews = repo.save(news);

        Assertions.assertThat(savedNews).isNotNull();
        Assertions.assertThat(savedNews.getNewsID()).isGreaterThan(0);

    }

       @Test 
    public void testListAll() {
       Iterable<News> newss = repo.findAll();
       Assertions.assertThat(newss).hasSizeGreaterThan(0);

       for (News news : newss) {
            System.out.println(news);
       }
    }

     @Test
    public void testUpdate() {
        Integer newsId = 4;
        Optional<News> optionalNews = repo.findById(newsId);
        News news = optionalNews.get();
        news.setContent("CGV tang ly moi");;
        repo.save(news);

        News updatedNews = repo.findById(newsId).get();
        Assertions.assertThat(updatedNews.getContent()).isEqualTo("CGV tang ly moi");
    }

    
    @Test
    public void testGet() {
        Integer newsId = 5;
        Optional<News> optionalNews = repo.findById(newsId);
        Assertions.assertThat(optionalNews).isPresent();
        System.out.println(optionalNews.get());
    }

     @Test
    public void testDelete() {
        Integer newsId = 4;
        repo.deleteById(newsId);
        Optional<News> optionalNews = repo.findById(newsId);
        Assertions.assertThat(optionalNews).isNotPresent();


    }











}