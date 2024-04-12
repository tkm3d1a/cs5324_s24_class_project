package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.IdListDTO;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/display", produces = "application/json")
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;

    @GetMapping("/media")
    public ResponseEntity<?> getMediaToDisplay(){
        Map<String,Object> response = new LinkedHashMap<>();
        List<Media> displaySvcReturn = displayService.getMediaToDisplay();
        response.put("count", displaySvcReturn.size());
        response.put("media", displaySvcReturn);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/media")
    public ResponseEntity<?> updateTaggedMedia(@RequestBody IdListDTO listOfIds){
        Map<String,Object> response = new LinkedHashMap<>();
        List<String> newMediaIds = listOfIds.getIds();
        List<Media> displaySvcReturn = displayService.tagMediaForDisplay(newMediaIds);
        response.put("count", displaySvcReturn.size());
        response.put("taggedMedia", displaySvcReturn);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPostsToDisplay(){
        Map<String,Object> response = new LinkedHashMap<>();
        List<Post> displaySvcReturn = displayService.getPostToDisplay();
        response.put("count", displaySvcReturn.size());
        response.put("posts", displaySvcReturn);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> updateTaggedPosts(@RequestBody IdListDTO listOfIds){
        Map<String,Object> response = new LinkedHashMap<>();
        List<String> newPostIds = listOfIds.getIds();
        List<Post> displaySvcReturn = displayService.tagPostForDisplay(newPostIds);
        response.put("count", displaySvcReturn.size());
        response.put("taggedPosts", displaySvcReturn);
        return ResponseEntity.ok().body(response);
    }
}
