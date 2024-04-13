package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/notifications", produces = "application/json")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
}
