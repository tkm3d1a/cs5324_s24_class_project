package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.exception.PostNotFoundException;
import com.cs5324.monitorbackend.repository.PostRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                () -> {throw new PostNotFoundException("Post not found for the user", userId);}
        );
        return posts;
    }

    public void deletePostByID(UUID id) {
        postRepo.deleteById(id);
    }

    public Post editPost(Post post) throws PostNotFoundException {
        Optional<Post> postToEdit = postRepo.findById(post.getId());
        if(postToEdit.isPresent()) {
            Post editedPost = postToEdit.get();
            if(!post.getTitle().isBlank()) editedPost.setTitle(post.getTitle());
            if(!post.getContent().isBlank()) editedPost.setContent(post.getContent());
            if(!post.getContent().isBlank()) editedPost.setContent(post.getContent());
            editedPost.setStatus(ItemStatus.PENDING);
            editedPost.setIsTagged(false);
            if(null != post.getMedia()) editedPost.setMedia(post.getMedia());
            return postRepo.save(editedPost);
        } else {
            throw new PostNotFoundException(post.getId());
        }
    }
}
