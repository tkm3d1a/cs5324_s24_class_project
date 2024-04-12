package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final NotificationService notificationService;

    public Iterable<Notification> getAllNotifications() {
        Iterable<Notification> notifications = notificationService.getNotifications();
        if (!notifications.iterator().hasNext()) throw new EntityNotFoundException("No notifications found.");

        Set<Notification> notificationSet = new HashSet<>();
        notifications.forEach(notificationSet::add);

        Set<Notification> notApprovedNotifications = notificationSet.stream().filter(notification -> {
            Event e = notification.getEvent();
            Media m = notification.getMedia();
            Post p = notification.getPost();

            if (e != null && e.getApprovalStatus() != ItemStatus.APPROVED) return true;
            if (m != null && m.getItemStatus() != ItemStatus.APPROVED) return true;
            return p != null && p.getStatus() != ItemStatus.APPROVED;
        }).collect(Collectors.toSet());

        return notApprovedNotifications;

        // Get all notifications where the post, event, or media has not been approved.
    }

    public Notification reviewForApproval(@Valid Notification notification, boolean isApproved) throws BadRequestException {
        return notificationService.reviewForApproval(notification, isApproved);
    }
}
