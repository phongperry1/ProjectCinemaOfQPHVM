package com.example;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.mo.Chat;
import com.example.mo.Promotions;
import com.example.mo.Users;
import com.example.CRUD.Repository.PromotionsRepository;
import com.example.CRUD.service.ChatService;
import com.example.CRUD.service.PromotionsService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Retrieve beans after the application context has been initialized
        PromotionsRepository promotionsRepository = context.getBean(PromotionsRepository.class);
        PromotionsService promotionsService = context.getBean(PromotionsService.class);
        ChatService chatService = context.getBean(ChatService.class);
        // Use the services provided by the beans
       
        Users user1 = new Users();
        user1.setUserId(1);
        user1.setUserName("John Doe");

        Users user2 = new Users();
        user2.setUserId(2);
        user2.setUserName("Jane Smith");

        

        // Get the chat messages between the two users
        List<Chat> chats = chatService.getChatsBetweenUsers(user1, user2);

        // Print the chat messages
        for (Chat chat : chats) {
            System.out.println("Sender: " + chat.getSender().getUserName());
            System.out.println("Receiver: " + chat.getReceiver().getUserName());
            System.out.println("Message: " + chat.getMessage());
            System.out.println("Timestamp: " + chat.getTimestamp());
            System.out.println();
        }
    }
        
    }

