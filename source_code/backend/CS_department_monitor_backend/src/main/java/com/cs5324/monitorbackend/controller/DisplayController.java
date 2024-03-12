package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/display", produces = "application/json")
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;
}
