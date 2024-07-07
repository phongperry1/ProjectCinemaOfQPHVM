package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.Chat;
import com.example.mo.Users;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findBySenderAndReceiver(Users sender, Users receiver);
    List<Chat> findBySenderAndReceiverOrReceiverAndSender(Users sender, Users receiver, Users receiver2, Users sender2);
}