package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/display", produces = "application/json")
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;

    @GetMapping("/media")
    public ResponseEntity<?> getMediaToDisplay(){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("count", displayService.getMediaToDisplay().size());
        response.put("media", displayService.getMediaToDisplay());
        return ResponseEntity.ok().body(response);
    }
}
