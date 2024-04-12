package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.IdListDTO;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.responsebody.GenericMediaResponse;
import com.cs5324.monitorbackend.responsebody.GenericPostResponse;
import com.cs5324.monitorbackend.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/display", produces = "application/json")
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;

    @GetMapping("/media")
    public ResponseEntity<?> getMediaToDisplay(){
        GenericMediaResponse mediaResponse = new GenericMediaResponse();
        List<Media> displaySvcReturn = displayService.getMediaToDisplay();
        mediaResponse.setCount(displaySvcReturn.size());
        mediaResponse.setMedia(displaySvcReturn);
        return ResponseEntity.ok().body(mediaResponse);
    }

    @PostMapping("/media")
    public ResponseEntity<?> updateTaggedMedia(@RequestBody IdListDTO listOfIds){
        GenericMediaResponse mediaResponse = new GenericMediaResponse();
        List<String> newMediaIds = listOfIds.getIds();
        List<Media> displaySvcReturn = displayService.tagMediaForDisplay(newMediaIds);
        mediaResponse.setCount(displaySvcReturn.size());
        mediaResponse.setMedia(displaySvcReturn);
        return ResponseEntity.ok().body(mediaResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPostsToDisplay(){
        GenericPostResponse postResponse = new GenericPostResponse();
        List<Post> displaySvcReturn = displayService.getPostToDisplay();
        postResponse.setCount(displaySvcReturn.size());
        postResponse.setPosts(displaySvcReturn);
        return ResponseEntity.ok().body(postResponse);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> updateTaggedPosts(@RequestBody IdListDTO listOfIds){
        GenericPostResponse postResponse = new GenericPostResponse();
        List<String> newPostIds = listOfIds.getIds();
        List<Post> displaySvcReturn = displayService.tagPostForDisplay(newPostIds);
        postResponse.setCount(displaySvcReturn.size());
        postResponse.setPosts(displaySvcReturn);
        return ResponseEntity.ok().body(postResponse);
    }
}
