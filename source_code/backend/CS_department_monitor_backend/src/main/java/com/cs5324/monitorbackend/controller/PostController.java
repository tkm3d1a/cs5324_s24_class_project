package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Post;

import com.cs5324.monitorbackend.exception.PostNotFoundException;
import com.cs5324.monitorbackend.responsebody.GenericPostResponse;
import com.cs5324.monitorbackend.responsebody.PostResponse;
import com.cs5324.monitorbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    //localhost:8080/api/posts?userId=2bcc6af8-4c97-4afc-96af-b03a128e8a32
    @GetMapping
    public ResponseEntity<List<PostResponse>> viewPosts(@RequestParam UUID userId) throws PostNotFoundException {
        return ResponseEntity.ok(postService.getPostsForUser(userId));
    }

    //localhost:8080/api/posts/822c747b-ce04-42ad-adf1-784e7caa2105
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
        postService.deletePostByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //http://localhost:8080/api/posts
    @PatchMapping(consumes = "application/json")
    public ResponseEntity<PostResponse> editPost(@RequestBody Post post) throws PostNotFoundException {
        return ResponseEntity.ok(postService.editPost(post));
    }

    //localhost:8080/api/posts/populate
    @GetMapping("/populate")
    public ResponseEntity<List<PostResponse>> populatePosts() {
        return ResponseEntity.ok(postService.populate());
    }

    @PostMapping("/populate/approved")
    public ResponseEntity<?> populateApprovedPosts(@RequestParam int postCount, @RequestParam int userCount){
        GenericPostResponse postResponse = new GenericPostResponse();
        List<Post> approvedPosts = postService.populateApprovedPosts(postCount,userCount);
        List<UUID> postIds = new ArrayList<>();
        for(Post post : approvedPosts){
            postIds.add(post.getId());
        }

        postResponse.setPostIds(postIds);
        postResponse.setPosts(approvedPosts);
        return ResponseEntity.ok().body(postResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<?> viewAllPosts() {
        GenericPostResponse postResponse = new GenericPostResponse();
        List<Post> allPosts = postService.getAll();
        postResponse.setCount(allPosts.size());
        postResponse.setPosts(allPosts);
        return ResponseEntity.ok().body(postResponse);
    }
}
