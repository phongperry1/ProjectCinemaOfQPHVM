package com.example.CRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.ScreeningRoomService;

import com.example.mo.ScreeningRoom;





@Controller
public class ScreeningRoomController {
    @Autowired private ScreeningRoomService service;

    @GetMapping("/screeningroom")
    public String showScreeningRoomList(Model model) {
        List<ScreeningRoom> listScreeningRoom = service.listAll();
        model.addAttribute("listScreeningRoom", listScreeningRoom);
        return "screeningroom"; 
    }

    @GetMapping("/screeningroom/new")
    public String showNewForm(Model model) {
        model.addAttribute("screeningroom", new ScreeningRoom());
        model.addAttribute("pageTitle", "Add New ScreeningRoom");
        return "screeningroom_form";
    }

    @PostMapping("/screeningroom/save")
    public String saveScreeningRoom(ScreeningRoom screeningroom, RedirectAttributes ra) {
        
        service.save(screeningroom);
        ra.addFlashAttribute("message", "The screeningroom has been saved successfully.");
        return "redirect:/screeningroom";
        
    }

     @GetMapping("screeningroom/edit/{ScreeningRoomID}")
    public String showEditForm(@PathVariable("ScreeningRoomID") Integer ScreeningRoomID, Model model, RedirectAttributes ra) {
        try {
            ScreeningRoom screeningroom = service.get(ScreeningRoomID);
            model.addAttribute("screeningroom", screeningroom);
            model.addAttribute("pageTitle", "Edit User (ID: " + ScreeningRoomID + ")");
            return "screeningroom_form";
        } catch (ScreeningRoomNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
             return "redirect:/screeningroom";
        }  
    }


    @GetMapping("screeningroom/delete/{ScreeningRoomID}")
    public String deleteShowtime(@PathVariable("ScreeningRoomID") Integer ScreeningRoomID, RedirectAttributes ra) {
        try {
            service.delete(ScreeningRoomID);
            
        } catch (ScreeningRoomNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/screeningroom";
        
        
    }








}