package com.example.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.ChatRepository;
import com.example.mo.Chat;
import com.example.mo.Users;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getChatsBetweenUsers(Users user1, Users user2) {
        return chatRepository.findBySenderAndReceiverOrReceiverAndSender(user1, user2, user1, user2);
    }

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }
}