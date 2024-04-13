package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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

    public Notification reviewForApproval(Notification notification, boolean isApproved) throws BadRequestException {
        if (notification.getId() == null) throw new BadRequestException();
        Notification n = notificationRepo.findById(notification.getId()).orElse(null);
        Post p = n.getPost();
        Event e = n.getEvent();
        Media m = n.getMedia();

        if(p != null) {
            p.setStatus(isApproved ? ItemStatus.APPROVED : ItemStatus.REJECTED);
        } else if (e != null) {
            e.setApprovalStatus(isApproved ? ItemStatus.APPROVED : ItemStatus.REJECTED);
        } else if (m != null) {
            m.setItemStatus(isApproved ? ItemStatus.APPROVED : ItemStatus.REJECTED);
        }

        return notificationRepo.save(n);
    }
}
