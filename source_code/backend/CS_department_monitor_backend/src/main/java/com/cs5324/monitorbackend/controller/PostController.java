package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/posts", produces = "application/json")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
}
