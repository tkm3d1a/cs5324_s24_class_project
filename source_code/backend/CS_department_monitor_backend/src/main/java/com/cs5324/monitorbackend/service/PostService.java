package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.repository.PostRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService{
    @Resource
    private final PostRepository postRepo;

    @Resource
    private final UserService userService;

    public List<Post> getPostsForUser(UUID userId) {
        List<Post> posts = new ArrayList<>();
        userService.getUser(userId).ifPresentOrElse(user -> posts.addAll(postRepo.findPostByUser(user)),
                () -> {throw new RuntimeException("User not found in DB");}
        );
        return posts;
    }
}
