package com.example.CRUD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.NotificationRepository;
import com.example.mo.Notification;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByStatusFalse();
    }
    public void markAllAsRead() {
        List<Notification> notifications = notificationRepository.findByStatusFalse();
        for (Notification notification : notifications) {
            notification.setStatus(true);
        }
        notificationRepository.saveAll(notifications);
    }
}