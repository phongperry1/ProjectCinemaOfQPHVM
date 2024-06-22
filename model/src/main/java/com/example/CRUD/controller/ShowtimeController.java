package com.example.CRUD.controller;

<<<<<<< HEAD
=======

>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
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

<<<<<<< HEAD
@Controller
public class ShowtimeController {
    @Autowired
    private ShowtimeService service;
    @Autowired
    private ShowtimeRepository showtimeRepository;
=======




@Controller
public class ShowtimeController {
    @Autowired private ShowtimeService service;
    @Autowired private ShowtimeRepository showtimeRepository;
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36

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
<<<<<<< HEAD

        if (result.hasErrors()) {
=======
        
        if(result.hasErrors()) {
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
            return "showtime_form";
        }

        try {
            Date showDate = Date.valueOf(showtime.getShowDate().toLocalDate().toString());
<<<<<<< HEAD
            Time showTime = Time.valueOf(showtime.getShowTime().toLocalTime());
=======
            Time showTime = Time.valueOf(showtime.getShowTime().toLocalTime()); 
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36

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

<<<<<<< HEAD
    @GetMapping("showtime/edit/{ShowtimeID}")
=======

     @GetMapping("showtime/edit/{ShowtimeID}")
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
    public String showEditForm(@PathVariable("ShowtimeID") Integer ShowtimeID, Model model, RedirectAttributes ra) {
        try {
            Showtime showtime = service.get(ShowtimeID);
            model.addAttribute("showtime", showtime);
            model.addAttribute("pageTitle", "Edit User (ID: " + ShowtimeID + ")");
            return "showtime_form";
        } catch (ShowtimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
<<<<<<< HEAD
            return "redirect:/showtime";
        }

=======
             return "redirect:/showtime";
        }
        
        
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
    }

    @GetMapping("showtime/delete/{ShowtimeID}")
    public String deleteShowtime(@PathVariable("ShowtimeID") Integer ShowtimeID, RedirectAttributes ra) {
        try {
            service.delete(ShowtimeID);
<<<<<<< HEAD

=======
            
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
        } catch (ShowtimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/showtime";
<<<<<<< HEAD

    }

=======
        
        
    }











>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
}