package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.repository.PostRepository;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService{
    @Resource
    private final PostRepository postRepo;

    public Post createPost(@Valid Post post) {
        return postRepo.save(post);
    }
}
