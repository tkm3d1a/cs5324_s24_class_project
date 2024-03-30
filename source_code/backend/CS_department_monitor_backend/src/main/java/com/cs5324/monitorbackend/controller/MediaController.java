package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.MediaDTO;
import com.cs5324.monitorbackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/media", produces = "application/json")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @GetMapping
    public ResponseEntity<?> getAllMediaDefault(){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("count",mediaService.getAllMediaSortByCreatedDesc().size());
        response.put("media",mediaService.getAllMediaSortByCreatedDesc());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createNewMedia(@RequestBody MediaDTO mediaDTO){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("result", "successful");
        response.put("savedMedia", mediaService.createNewMediaEntry(mediaDTO));
        return ResponseEntity.ok().body(response);
    }
}
