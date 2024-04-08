package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService{
    private final NotificationRepository notificationRepo;

    public Iterable<Notification> getNotifications() {
        return notificationRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepo.save(notification);
    }
}
