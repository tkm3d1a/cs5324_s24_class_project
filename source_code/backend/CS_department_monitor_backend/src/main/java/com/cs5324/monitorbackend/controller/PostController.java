package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/posts", produces = "application/json")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            return ResponseEntity.ok(postService.createPost(post));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
