package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.exception.PostNotFoundException;
import com.cs5324.monitorbackend.repository.PostRepository;
import com.cs5324.monitorbackend.responsebody.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepo;

    private final UserService userService;
  
    public Post createPost(@Valid Post post) {
      Notification n = new Notification();
      n.setPost(post);
      post.setNotification(n);
        return postRepo.save(post);
    }

    public List<PostResponse> getPostsForUser(UUID userId) {
        log.info("Retrieving posts created by the user with userId: {}", userId);
        List<Post> posts = new ArrayList<>();
        userService.getUser(userId).ifPresentOrElse(user -> posts.addAll(postRepo.findPostByUser(user)),
                () -> {
                    throw new PostNotFoundException("Post not found for the user", userId);
                }
        );
        return posts.stream().map(this::mapPostResponse).toList();
    }

    public void deletePostByID(UUID id) {
        log.info("Deleting the post with id: {}", id);
        postRepo.deleteById(id);
    }

    public PostResponse editPost(Post post) throws PostNotFoundException {
        log.info("Updating the post with id: {}", post.getId());
        Optional<Post> postToEdit = postRepo.findById(post.getId());
        if(postToEdit.isPresent()) {
            Post editedPost = postToEdit.get();
            if(!post.getTitle().isBlank()) editedPost.setTitle(post.getTitle());
            if(!post.getContent().isBlank()) editedPost.setContent(post.getContent());
            if(!post.getContent().isBlank()) editedPost.setContent(post.getContent());
            editedPost.setStatus(ItemStatus.PENDING);
            editedPost.setIsTagged(false);
            if(null != post.getMedia()) editedPost.setMedia(post.getMedia());
            return mapPostResponse(postRepo.save(editedPost));
        } else {
            throw new PostNotFoundException(post.getId());
        }
    }

    public List<Post> getAll() {
      return postRepo.findAll(Sort.by("createdAt").descending());
    }

    private PostResponse mapPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .createdBy(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .media(post.getMedia())
                .status(post.getStatus())
                .isTagged(post.getIsTagged())
                .createdOn(post.getCreatedAt())
                .build();
    }

    public List<PostResponse> populate() {
        User user = new User();
        user.setUsername("test_user1");
        user.setPassword("password");

        Post post1 = new Post();
        post1.setUser(user);
        post1.setTitle("Post1 Title Populated");
        post1.setContent("Lorem1 Epsum1 Content1");

        Post post2 = new Post();
        post2.setUser(user);
        post2.setTitle("Post2 Title Populated");
        post2.setContent("Lorem2 Epsum2 Content2");

        user.addPost(post1);
        user.addPost(post2);

        userService.saveUser(user);
        postRepo.save(post1);
        postRepo.save(post2);

        User user2 = new User();
        user2.setUsername("test_user2");
        user2.setPassword("password");

        Post post3 = new Post();
        post3.setUser(user2);
        post3.setTitle("Post3 Title Populated - APPROVED");
        post3.setContent("Lorem3 Epsum3 Content3");
        post3.setStatus(ItemStatus.APPROVED);

        user2.addPost(post3);
        userService.saveUser(user2);
        postRepo.save(post3);

        return postRepo.findAll().stream().map(this::mapPostResponse).toList();

    }

    public List<Post> populateApprovedPosts(int countToPopulate, int userCount){
        List<Post> populatedPosts = new ArrayList<>();
        for(int i = 0; i < countToPopulate;i++){
            int userId = (int)(Math.random() * userCount+1);
            populatedPosts.add(populateSinglePost(i,userId));
        }
        return populatedPosts;
    }

    private Post populateSinglePost(int i,int userId) {
        Post post = new Post();
        String username = "Populate user " + userId;
        User user = userService.findByUsername(username);
        if(user.getId() == null){
            user = new User();
            user.setEnabled(true);
            user.setUsername(username);
            user.setPassword("password");
            userService.saveUser(user);
        }
        log.info("user: {}", user);
        post.setUser(user);
        post.setTitle("APPROVED - Post " + i + " | user " + username);
        post.setContent("Lorem ipsum lorem ipsum lorem ipsum " + username + " " + i);
        post.setStatus(ItemStatus.APPROVED);
        user.addPost(post);
        userService.saveUser(user);
        return postRepo.save(post);
    }

    public List<Post> getPostsByTagStatus() {
        return postRepo.findByIsTaggedOrderByCreatedAtDesc(true);
    }

    public List<Post> getPostsByApprovalStatus(ItemStatus status) {
        List<Post> approvedPosts = postRepo.findByStatusIn(List.of(status));
        approvedPosts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return approvedPosts;
    }

    public Post getPostById(UUID postIdConverted) {
        log.info("Getting existing media entry");
        Optional<Post> postToUpdateOpt = postRepo.findById(postIdConverted);
        if(postToUpdateOpt.isEmpty()){
            log.warn("Media not found");
            throw new PostNotFoundException(postIdConverted);
        }
        return postToUpdateOpt.get();
    }

    public List<Post> updateTagStatus(List<Post> newTagged, List<Post> oldTagged) {
        for(Post oldPost : oldTagged){
            log.info("Updating oldPost: {}", oldPost);
            oldPost.setIsTagged(false);
            postRepo.save(oldPost);
        }

        List<Post> confirmationPosts = new LinkedList<>();
        for(Post newPost : newTagged){
            log.info("Updating newPost: {}", newPost);
            newPost.setIsTagged(true);
            confirmationPosts.add(postRepo.save(newPost));
        }

        return confirmationPosts;
    }
}
