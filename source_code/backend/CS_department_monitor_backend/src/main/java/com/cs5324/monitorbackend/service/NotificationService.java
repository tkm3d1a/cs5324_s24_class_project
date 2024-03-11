package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.repository.NotificationRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService{
    @Resource
    private final NotificationRepository notificationRepo;
}
