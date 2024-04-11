package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import jakarta.mail.FetchProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisplayService{
    private final PostService postService;
    private final PageService pageService;
    private final MediaService mediaService;
    private final EventService eventService;

    public List<Media> getMediaToDisplay(){
        List<Media> eligibleMedia = mediaService.getMediaByTagStatus();
        if(eligibleMedia.size() < 10){
            List<Media> sortedMedia = mediaService.getMediaByApprovalStatus(ItemStatus.APPROVED);
            while(eligibleMedia.size() < 10){
                eligibleMedia.add(sortedMedia.remove(0));
            }
        }
        return eligibleMedia;
    }

    public List<Post> getPostToDisplay(){
        //TODO: Create required method for posts
        //return postService.getAllPosts();
        return null;
    }
    public List<Event> getEventToDisplay(){
        //TODO: Create required method for events
        //return eventService.getAllEvents();
        return null;
    }

    public List<Media> mediaEligibleForDisplay(){
        return mediaService.getMediaByApprovalStatus(ItemStatus.APPROVED);
    }

    public List<Media> tagMediaForDisplay(List<Media> newTaggedMedia){
        List<Media> currentTaggedMedia = mediaService.getMediaByTagStatus();

        boolean listMatchStatus = true;
        for(Media newMedia : newTaggedMedia){
            if (!currentTaggedMedia.contains(newMedia)) {
                listMatchStatus = false;
                break;
            }
        }
        if(listMatchStatus){
            return mediaService.updateTagStatus(newTaggedMedia, currentTaggedMedia);
        } else {
            return currentTaggedMedia;
        }
    }
}
