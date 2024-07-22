package com.example.CRUD.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CRUD.service.ChatService;
import com.example.CRUD.service.NotificationService;
import com.example.CRUD.service.UserService;
import com.example.mo.Chat;
import com.example.mo.Notification;
import com.example.mo.Users;

@Controller
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final NotificationService notificationService;

    public ChatController(ChatService chatService, UserService userService, NotificationService notificationService) {
        this.chatService = chatService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping("/api/chats/{recipientId}")
    @ResponseBody
    public List<Chat> getNewChats(@PathVariable Integer recipientId, Principal principal) {
        String email = principal.getName();
        Users currentUser = userService.getUsersByEmail(email);
        Users recipient = userService.getUserById(recipientId);
        List<Chat> chats = chatService.getChatsBetweenUsers(currentUser, recipient)
                .stream()
                .sorted(Comparator.comparing(Chat::getTimestamp))
                .collect(Collectors.toList());
        return chats;
    }

    @GetMapping("/chat/{recipientId}")
    public String getChatPage(@PathVariable Integer recipientId, Principal principal, Model model) {
        String email = principal.getName();
        Users currentUser = userService.getUsersByEmail(email);
        Users recipient = userService.getUserById(recipientId);
        List<Chat> chats = chatService.getChatsBetweenUsers(currentUser, recipient)
                .stream()
                .sorted(Comparator.comparing(Chat::getTimestamp))
                .collect(Collectors.toList());
        model.addAttribute("chats", chats);
        model.addAttribute("user", currentUser);
        model.addAttribute("recipient", recipient);
        model.addAttribute("currentUserName", currentUser.getUserName());
        return "chat";
    }

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute("message") String message, @RequestParam("receiverId") Integer receiverId,
            Principal principal, Model model) {
        String email = principal.getName();
        Users currentUser = userService.getUsersByEmail(email);
        Users recipient = userService.getUserById(receiverId);
        if (!currentUser.getUserName().equals("ADMIN")) {
            createNotificationForAdmin(currentUser, message);
        }
        Chat chat = new Chat();
        chat.setSender(currentUser);
        chat.setReceiver(recipient);
        chat.setMessage(message);
        chat.setTimestamp(new Timestamp(System.currentTimeMillis()));
        chatService.saveChat(chat);
        return "redirect:/chat/" + receiverId;
    }

    private void createNotificationForAdmin(Users currentUser, String message) {
        Notification notification = new Notification();
        notification.setMessage("User " + currentUser.getUserName() + " đã nhắn tin cho bạn: " + message);
        notification.setStatus(false);
        notification.setTimestamp(new Timestamp(System.currentTimeMillis()));
        notificationService.saveNotification(notification);
    }
}
