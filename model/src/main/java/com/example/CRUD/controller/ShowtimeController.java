package com.example.CRUD.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.Repository.ShowtimeRepository;
import com.example.CRUD.service.ShowtimeService;
import com.example.mo.Showtime;

@Controller
public class ShowtimeController {
    @Autowired
    private ShowtimeService service;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @GetMapping("/showtime")
    public String showTheaterList(Model model) {
        List<Showtime> listShowtime = service.listAll();
        model.addAttribute("listShowtime", listShowtime);
        return "showtime";
    }

    @GetMapping("/showtime/new")
    public String showNewForm(Model model) {
        model.addAttribute("showtime", new Showtime());
        model.addAttribute("pageTitle", "Add New User");
        return "showtime_form";
    }

    @PostMapping("/showtime/save")
    public String saveShowtime(@ModelAttribute Showtime showtime, RedirectAttributes ra, BindingResult result) {

        if (result.hasErrors()) {
            return "showtime_form";
        }

        try {
            Date showDate = Date.valueOf(showtime.getShowDate().toLocalDate().toString());
            Time showTime = Time.valueOf(showtime.getShowTime().toLocalTime());

            // Đặt lại các giá trị đã chuyển đổi vào đối tượng showtime
            showtime.setShowDate(showDate);
            showtime.setShowTime(showTime);

            // Lưu đối tượng showtime vào cơ sở dữ liệu
            showtimeRepository.save(showtime);

            // Thêm thông báo thành công và chuyển hướng
            ra.addFlashAttribute("message", "The showtime has been saved successfully.");
            return "redirect:/showtime";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "Invalid date or time format.");
            return "redirect:/showtime";
        }
    }

    @GetMapping("showtime/edit/{ShowtimeID}")
    public String showEditForm(@PathVariable("ShowtimeID") Integer ShowtimeID, Model model, RedirectAttributes ra) {
        try {
            Showtime showtime = service.get(ShowtimeID);
            model.addAttribute("showtime", showtime);
            model.addAttribute("pageTitle", "Edit User (ID: " + ShowtimeID + ")");
            return "showtime_form";
        } catch (ShowtimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/showtime";
        }

    }

    @GetMapping("showtime/delete/{ShowtimeID}")
    public String deleteShowtime(@PathVariable("ShowtimeID") Integer ShowtimeID, RedirectAttributes ra) {
        try {
            service.delete(ShowtimeID);

        } catch (ShowtimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/showtime";

    }

}