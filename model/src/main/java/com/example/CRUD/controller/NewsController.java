package com.example.CRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.NewsService;

import com.example.mo.News;





@Controller
public class NewsController {
   @Autowired private NewsService service;

    @GetMapping("/news")
    public String showNewsList(Model model) {
        List<News> listNews = service.listAll();
        model.addAttribute("listNews", listNews);
        return "news"; 
    }

    @GetMapping("/news/new")
    public String showNewForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("pageTitle", "Add New News");
        return "news_form";
    }

    @PostMapping("/news/save")
    public String saveNews(News news, RedirectAttributes ra) {
        
        service.save(news);
        ra.addFlashAttribute("message", "The news has been saved successfully.");
        return "redirect:/news";
        
    }

     @GetMapping("news/edit/{NewsID}")
    public String showEditForm(@PathVariable("NewsID") Integer NewsID, Model model, RedirectAttributes ra) {
        try {
            News news = service.get(NewsID);
            model.addAttribute("news", news);
            model.addAttribute("pageTitle", "Edit User (ID: " + NewsID + ")");
            return "news_form";
        } catch (NewsNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
             return "redirect:/news";
        }
        
        
    }


    @GetMapping("news/delete/{NewsID}")
    public String deleteNews(@PathVariable("NewsID") Integer NewsID, RedirectAttributes ra) {
        try {
            service.delete(NewsID);
            
        } catch (NewsNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/news";
        
        
    }



}