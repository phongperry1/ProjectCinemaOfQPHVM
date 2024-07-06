package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatusFalse();
}
