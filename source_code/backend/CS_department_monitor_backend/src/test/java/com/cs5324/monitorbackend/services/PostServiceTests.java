package com.cs5324.monitorbackend.services;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.exception.PostNotFoundException;
import com.cs5324.monitorbackend.repository.PostRepository;
import com.cs5324.monitorbackend.responsebody.PostResponse;
import com.cs5324.monitorbackend.service.PostService;
import com.cs5324.monitorbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private UserService userService;

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
        testSavedPost.setUser(User.builder().id(UUID.randomUUID()).email("email@user.com").username("user1").build());
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

    @Test
    public void getPostsForUserTest() {
        when(userService.getUser(any(UUID.class)))
                .thenReturn(Optional.ofNullable(User.builder().email("email@user.com").username("user1").build()));
        when(postRepository.findPostByUser(any(User.class))).thenReturn(Collections.singletonList(testSavedPost));

        List<PostResponse> postsForUser = postService.getPostsForUser(UUID.randomUUID());

        assertEquals(1, postsForUser.size());
        assertEquals(postsForUser.get(0).getTitle(), "Post Test");
        assertEquals(postsForUser.get(0).getContent(), "Test Content");
        assertEquals(postsForUser.get(0).getStatus(), ItemStatus.PENDING);
    }

    @Test
    public void deletePostByIDTest() {
        assertDoesNotThrow(() -> postService.deletePostByID(UUID.randomUUID()));
    }

    @Test
    public void editPostTest() {
        Post updatedPost = testSavedPost;
        updatedPost.setContent("UpdatedContent");
        updatedPost.setTitle("UpdatedTitle");

        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(testSavedPost));
        when(postRepository.save(testSavedPost)).thenReturn(updatedPost);

        PostResponse postResponse = postService.editPost(testSavedPost);

        assertEquals(postResponse.getStatus(), ItemStatus.PENDING);
        assertEquals(postResponse.getContent(), "UpdatedContent");
        assertEquals(postResponse.getTitle(), "UpdatedTitle");
    }

    @Test
    public void editPost_throws_exception_when_post_not_exist() {
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.editPost(testSavedPost));
    }
}
