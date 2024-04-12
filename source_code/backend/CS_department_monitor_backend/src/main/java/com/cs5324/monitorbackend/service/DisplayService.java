package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisplayService{
    private final PostService postService;
    private final PageService pageService;
    private final MediaService mediaService;
    private final EventService eventService;

    public List<Media> getMediaToDisplay(){
        List<Media> taggedMedia = mediaService.getMediaByTagStatus();
        log.info("taggedMedia Size: {}", taggedMedia.size());
        if(taggedMedia.size() < 10){
            List<Media> sortedMedia = mediaEligibleForDisplay();
            for(Media tagged : taggedMedia){
                sortedMedia.remove(tagged);
            }
            while(taggedMedia.size() < 10 && !sortedMedia.isEmpty()){
                taggedMedia.add(sortedMedia.remove(0));
            }
        }
        return taggedMedia;
    }

    public List<Post> getPostToDisplay(){
        List<Post> taggedPosts = postService.getPostsByTagStatus();
        log.info("taggedPosts Size: {}", taggedPosts.size());
        if(taggedPosts.size() < 10){
            List<Post> sortedPosts = postEligibleForDisplay();
            for(Post tagged : taggedPosts){
                sortedPosts.remove(tagged);
            }
            while(taggedPosts.size() < 10 && !sortedPosts.isEmpty()){
                taggedPosts.add(sortedPosts.remove(0));
            }
        }
        return taggedPosts;
    }

    private List<Post> postEligibleForDisplay() {
        return postService.getPostsByApprovalStatus(ItemStatus.APPROVED);
    }

    public List<Event> getEventToDisplay(){
        //TODO: Create required method for events
        //return eventService.getAllEvents();
        return null;
    }

    private List<Media> mediaEligibleForDisplay(){
        return mediaService.getMediaByApprovalStatus(ItemStatus.APPROVED);
    }

    public List<Media> tagMediaForDisplay(List<String> newTaggedMediaIds){
        List<Media> currentTaggedMedia = mediaService.getMediaByTagStatus();
        List<Media> newTaggedMedia = new ArrayList<>();
        for(String newMediaId : newTaggedMediaIds){
            log.info("Converting to UUID...");
            UUID mediaIdConverted;
            try {
                log.info("newMediaId: {}", newMediaId);
                mediaIdConverted = UUID.fromString(newMediaId);
                log.info("convertedId: {}", mediaIdConverted);
                Media newMedia = mediaService.getMediaById(mediaIdConverted);
                log.info("newMedia: {}", newMedia);
                newTaggedMedia.add(newMedia);
            } catch (Exception e) {
                log.error("error in converting MediaID: {}", e.getMessage());
                log.error("bad UUID passed: {}", newMediaId);
            }
        }

        return mediaService.updateTagStatus(newTaggedMedia, currentTaggedMedia);
    }

    public List<Post> tagPostForDisplay(List<String> newPostIds) {
        List<Post> currentTaggedPosts = postService.getPostsByTagStatus();
        List<Post> newTaggedPosts = new ArrayList<>();
        for(String newPostId : newPostIds){
            log.info("Converting to UUID...");
            UUID postIdConverted;
            try {
                log.info("newMediaId: {}", newPostId);
                postIdConverted = UUID.fromString(newPostId);
                log.info("convertedId: {}", postIdConverted);
                Post newPost = postService.getPostById(postIdConverted);
                log.info("newMedia: {}", newPost);
                newTaggedPosts.add(newPost);
            } catch (Exception e) {
                log.error("error in converting MediaID: {}", e.getMessage());
                log.error("bad UUID passed: {}", newPostId);
            }
        }

        return postService.updateTagStatus(newTaggedPosts, currentTaggedPosts);
    }
}
