package com.example.CRUD.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.Repository.CinemaOwnerRepository;
import com.example.CRUD.Repository.TicketRepository;
import com.example.CRUD.Repository.UserByAdminRepository;
import com.example.CRUD.service.CinemaService;
import com.example.CRUD.service.NotificationService;
import com.example.CRUD.service.TicketService;
import com.example.CRUD.service.UserByAdminService;
import com.example.CRUD.service.UserService;
import com.example.mo.CinemaOwner;
import com.example.mo.Notification;
import com.example.mo.Ticket;
import com.example.mo.Users;

// import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;

// @RestController
@Controller
@RequiredArgsConstructor
// @RequestMapping("/user")
public class UserByAdminControllere {

    @Autowired
    private UserByAdminService userByAdminService;
    private final UserByAdminRepository userByAdminRepository;
    private final TicketRepository ticketRepository;
    private final CinemaService cinemaService;
    private final CinemaOwnerRepository cinemaOwnerRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final TicketService ticketService;
    
      @GetMapping("/notifications")
    public String getNotifications(Model model, Principal principal) {
        String email = principal.getName();
        Users currentUser = userService.getUsersByEmail(email);

        if (currentUser.getUserName().equals("ADMIN")) { 
            List<Notification> notifications = notificationService.getUnreadNotifications();
            model.addAttribute("notifications", notifications);
        }

        return "notifications";
    }
    
    @PostMapping("/markNotificationsAsRead")
    @ResponseBody
    public String markNotificationsAsRead() {
        notificationService.markAllAsRead();
        return "success";
    }
    @GetMapping("/checkNewNotifications")
    @ResponseBody
    public List<Notification> checkNewNotifications(Principal principal) {
        String email = principal.getName();
        Users currentUser = userService.getUsersByEmail(email);

        if (currentUser.getUserName().equals("ADMIN")) { 
            return notificationService.getUnreadNotifications();
        }

        return List.of();
    }

    @GetMapping("/show")
public String getUserDetails(Model model, Principal principal) {
    String email = principal.getName();
    Users currentUser = userService.getUsersByEmail(email);
    List<Users> userByAdmins = userByAdminService.getUserByAdmins()
        .stream()
        .filter(user -> !user.getUserName().equals("ADMIN"))
        .collect(Collectors.toList());
    
    model.addAttribute("UserByAdmins", userByAdmins);

    if (currentUser.getUserName().equals("ADMIN")) { 
        List<Notification> notifications = notificationService.getUnreadNotifications();
        model.addAttribute("notifications", notifications);
        model.addAttribute("unreadCount", notifications.size());
    }

    return "userdetails";
}


    @PostMapping()
    public Users addTicket(@RequestBody Users userByAdmin) {
        return userByAdminService.addTicket(userByAdmin);
    }

    @GetMapping("/userdetails/{UserId}")
public String findById(@PathVariable("UserId") Integer UserId, Model model) {
    Users user = userByAdminService.findById(UserId);
    model.addAttribute("user", user);
    return "user-details"; 
}

@GetMapping("/search")
public String searchUsersByName(@RequestParam(name = "userName", required = false) String userName, Model model) {
    List<Users> userByAdmins;
    if (userName == null || userName.isEmpty()) {
        userByAdmins = userByAdminRepository.findAll();
    } else {
        userByAdmins = userByAdminRepository.findByUserNameContainingIgnoreCase(userName);
    }
    model.addAttribute("UserByAdmins", userByAdmins);
    model.addAttribute("userName", userName);
    return "userdetails";
}
@GetMapping("/updaterank/{UserId}")
public String updateUserRank(@PathVariable("UserId") Integer UserId, Model model, RedirectAttributes ra) {
    Users user = userByAdminService.findById(UserId);
    
    if (user.getMemberPoints()<200){
        user.setUserRank("None");
    }
    else if (user.getMemberPoints() >= 1000) {
        user.setUserRank("Gold");
    } else if (user.getMemberPoints() >= 500) {
        user.setUserRank("Silver");
    } else if (user.getMemberPoints() >= 200) {
        user.setUserRank("Bronze");
    } else {
        ra.addFlashAttribute("message", "Rank updated successfully!");
        model.addAttribute("error", "Insufficient member points to update rank.");
        return "redirect:/show";
    }
    
    userByAdminService.updateRankUser(user);
    model.addAttribute("message", "Rank updated successfully!");
    
    return "redirect:/show";
}
@GetMapping("/RankInfo/{UserId}")
public String showUpdateRankForm(@PathVariable("UserId") Integer UserId, Model model) {
    Users user = userByAdminService.findById(UserId);
    int currentPoints = user.getMemberPoints();
    String currentRank = user.getUserRank();
    String nextRank;
    int pointsNeeded;
    model.addAttribute("maxPoints", 1000);
    
    if (currentRank.equals("None")) {
        nextRank = "Bronze";
        pointsNeeded = 200 - currentPoints;
    } else if (currentRank.equals("Bronze")) {
        nextRank = "Silver";
        pointsNeeded = 500 - currentPoints;
    } else if (currentRank.equals("Silver")) {
        nextRank = "Gold";
        pointsNeeded = 1000 - currentPoints;
    } else {
        nextRank = "Gold";
        pointsNeeded = 0;
    }
    
    model.addAttribute("user", user);
    model.addAttribute("currentPoints", currentPoints);
    model.addAttribute("currentRank", currentRank);
    model.addAttribute("nextRank", nextRank);
    model.addAttribute("pointsNeeded", pointsNeeded);
    model.addAttribute("showUpdateBadge", pointsNeeded == 0);
    return "update-rank";
}

@GetMapping("/lockaccount/{UserId}")
public String lockAccount(@PathVariable("UserId") Integer userId, RedirectAttributes ra) {
    Users user = userByAdminService.findById(userId);
    user.setStatus(!user.isStatus()); 
    userByAdminService.saveUserProfile(user);
    
    ra.addFlashAttribute("message", "Account status updated successfully!");
    return "redirect:/show";
}

@PostMapping("/updateAllUsers")
    public String updateAllUsers() {
        userByAdminService.updateAllUsers();
        return "redirect:/show";
    }
@GetMapping("/purchasehistory/{UserId}")
public String getPurchaseHistory(@PathVariable("UserId") Integer userId, Model model){
    List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
    model.addAttribute("tickets", tickets);
    return "puchar-info";
}

    // @GetMapping("/{id}")
    // public UserByAdmin findById(@PathVariable("id") Long id) {
    //     return userByAdminService.findById("id");
    // }

     @GetMapping("/cinema_owner")
    public String getCinemaOwner(Model model){
        List<CinemaOwner> cinemaOwners = cinemaService.getCinemaOwnerByAdmins();
        model.addAttribute("cinemaOwners", cinemaOwners);
        return "cinemaowner";
    }

    @GetMapping("/search_cinema_owner")
public String searcgCinemaOwner(@RequestParam(name = "cinemaName", required = false) String cinemaName, Model model) {
    List<CinemaOwner> cinemaOwners;
    if (cinemaName == null || cinemaName.isEmpty()) {
        cinemaOwners = cinemaOwnerRepository.findAll();
    } else {
        cinemaOwners = cinemaOwnerRepository.findByCinemaNameContainingIgnoreCase(cinemaName);
    }
    model.addAttribute("cinemaOwners", cinemaOwners);
    model.addAttribute("cinemaName", cinemaName);
    return "cinemaowner";
}
@GetMapping("/search_ticket")
public String searchTickByMovieName(@RequestParam(name = "moiveName", required = false) String moiveName, Model model) {
    List<Ticket> tickets;
    if (moiveName == null || moiveName.isEmpty()) {
        tickets = ticketService.getAllTickets();
    } else {
        tickets = ticketRepository.findTicketsByMovieTitleContainingIgnoreCase(moiveName);
    }
    model.addAttribute("tickets", tickets);
    model.addAttribute("moiveName", moiveName);
    return "puchar-info";
}

}