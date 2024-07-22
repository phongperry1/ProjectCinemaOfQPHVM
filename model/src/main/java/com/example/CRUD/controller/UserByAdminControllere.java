package com.example.CRUD.controller;

import com.example.CRUD.Repository.UserByAdminRepository;
import com.example.CRUD.service.NotificationService;
import com.example.CRUD.service.PurchaseHistoryService;
import com.example.CRUD.service.UserByAdminService;
import com.example.CRUD.service.UserService;
import com.example.mo.Notification;

// import Util.QRCodeGenerator;

import com.example.mo.PurchaseHistory;
import com.example.mo.Users;

// import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

// @RestController
@Controller
@RequiredArgsConstructor
// @RequestMapping("/user")
public class UserByAdminControllere {

    @Autowired
    private UserByAdminService userByAdminService;
    private final UserByAdminRepository userByAdminRepository;
    private final PurchaseHistoryService historyService;
    private final UserService userService;
    private final NotificationService notificationService;


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
    boolean isResetAllowed = userByAdminService.isResetAllowed();
    List<Users> userByAdmins = userByAdminService.getUserByAdmins();
    model.addAttribute("UserByAdmins", userByAdmins);
    String email = principal.getName();
    Users currentUser = userService.getUsersByEmail(email);
    model.addAttribute("isResetAllowed", isResetAllowed);
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

        if (user.getMemberPoints() < 200) {
            user.setUserRank("None");
        } else if (user.getMemberPoints() >= 1000) {
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
        ra.addFlashAttribute("message", "Rank updated successfully!");
        return "redirect:/show";
    }
    @PostMapping("/updateAllUsers")
    public String updateAllUsers() {
        userByAdminService.updateAllUsers();
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

    @GetMapping("/lockacount/{UserId}")
    public String lockAccount(@PathVariable("UserId") Integer userId, Model model) {
        Users user = userByAdminService.findById(userId);
        user.setStatus(!user.isStatus());
        userByAdminService.saveUser(user);

        model.addAttribute("message", "Account status updated successfully!");
        return "redirect:/show";
    }

    @GetMapping("/purchasehistory/{UserId}")
    public String getPurchaseHistory(@PathVariable("UserId") Integer userId, Model model) {
        List<PurchaseHistory> purchaseHistories = historyService.getPurchaseHistoryById(userId);
        model.addAttribute("purchaseHistories", purchaseHistories);
        return "test";
    }


}
