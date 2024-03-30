package com.cs5324.monitorbackend.services;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.repository.PostRepository;
import com.cs5324.monitorbackend.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post testPost;
    private Post testSavedPost;

    @BeforeEach
    public void setup() {
        testSavedPost = new Post();
        testSavedPost.setId(UUID.randomUUID());
        testSavedPost.setCreatedAt(LocalDateTime.now());
        testSavedPost.setVersion(1L);
        testSavedPost.setTitle("Post Test");
        testSavedPost.setContent("Test Content");
        testSavedPost.setStatus(ItemStatus.PENDING);
        testSavedPost.setIsTagged(false);
    }

    @Test
    public void createPost_ValidPost() {
        Post p = new Post();
        p.setTitle("Post Test");
        p.setContent("Test Content");
        p.setStatus(ItemStatus.PENDING);
        p.setIsTagged(false);

        when(postRepository.save(any(Post.class))).thenReturn(testSavedPost);

        Post s = postService.createPost(p);

        assertEquals(testSavedPost, s);
    }
}
