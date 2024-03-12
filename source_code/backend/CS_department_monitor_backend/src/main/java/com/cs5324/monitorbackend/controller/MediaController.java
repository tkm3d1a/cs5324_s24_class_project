package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/media", produces = "application/json")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
}
