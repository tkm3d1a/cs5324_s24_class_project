package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.exception.PostNotFoundException;
import com.cs5324.monitorbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/posts", produces = "application/json")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //localhost:8080/api/posts?userId=2bcc6af8-4c97-4afc-96af-b03a128e8a32
    @GetMapping
    public ResponseEntity<List<Post>> viewPosts(@RequestParam UUID userId) throws PostNotFoundException {
        return ResponseEntity.ok(postService.getPostsForUser(userId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
        postService.deletePostByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Post> editPost(@RequestBody Post post) throws PostNotFoundException {
        return ResponseEntity.ok(postService.editPost(post));
    }
}
