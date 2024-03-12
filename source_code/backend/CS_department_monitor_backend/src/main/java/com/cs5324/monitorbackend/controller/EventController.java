package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/events", produces = "application/json")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
}
