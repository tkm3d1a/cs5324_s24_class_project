package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.MediaDTO;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.entity.enums.MediaType;
import com.cs5324.monitorbackend.exception.InvalidMediaTypeException;
import com.cs5324.monitorbackend.exception.MediaDoesNotExistException;
import com.cs5324.monitorbackend.repository.MediaRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService{
    @Resource private final MediaRepository mediaRepo;

    //CREATE
    public Media createNewMediaEntry(MediaDTO mediaDTO){
        log.info("Creating new media entry");
        Media newMedia = new Media();
        newMedia.setTitle(mediaDTO.getTitle());
        newMedia.setLink(mediaDTO.getLink());
        switch(mediaDTO.getMediaType().toLowerCase()){
            case "picture","pic","image" -> newMedia.setMediaType(MediaType.IMAGE);
            case "vid","video","movie" -> newMedia.setMediaType(MediaType.VIDEO);
            default -> throw new InvalidMediaTypeException();
        }
        return mediaRepo.save(newMedia);
    }
    public List<Media> populate() {
        List<Media> populatedMedia = new ArrayList<>();
        int numberToCreate = 10;
        for(int i = 0; i < numberToCreate; i++){
            populatedMedia.add(populateSingleMediaImage(i));
        }
        return populatedMedia;
    }

    private Media populateSingleMediaImage(int count) {
        Media newMedia = new Media();
        newMedia.setMediaType(MediaType.IMAGE);
        newMedia.setLink("Dummy link " + count);
        newMedia.setTitle("Dummy Title " + count);
        newMedia.setItemStatus(ItemStatus.APPROVED);
        return mediaRepo.save(newMedia);
    }

    //READ
    public List<Media> getAllMediaSortByCreatedDesc(){
        return mediaRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
    public Media getMediaById(UUID mediaId){
        log.info("Getting existing media entry");
        Optional<Media> mediaToUpdateOpt = mediaRepo.findById(mediaId);
        if(mediaToUpdateOpt.isEmpty()){
            log.warn("Media not found");
            throw new MediaDoesNotExistException();
        }
        return mediaToUpdateOpt.get();
    }
    public List<Media> getMediaByType(String mediaType){
        return null;
    }
    public List<Media> getMediaByApprovalStatus(ItemStatus status) {
        List<Media> approvedMedia = mediaRepo.findByItemStatusIn(List.of(status));
        approvedMedia.sort(Comparator.comparing(Media::getCreatedAt).reversed());
        return approvedMedia;
    }
    public List<Media> getMediaByTagStatus() {
        return mediaRepo.findByIsTaggedOrderByCreatedAtDesc(true);
    }
    //UPDATE
    public Media updateMedia(UUID mediaId, MediaDTO mediaDTO){
        log.info("Updating existing media entry");
        Optional<Media> mediaToUpdateOpt = mediaRepo.findById(mediaId);
        if(mediaToUpdateOpt.isEmpty()){
            log.warn("Media not found");
            throw new MediaDoesNotExistException();
        }
        Media mediaToUpdate = mediaToUpdateOpt.get();
        mediaToUpdate.setTitle(mediaDTO.getTitle());
        mediaToUpdate.setLink(mediaDTO.getLink());
        if(mediaToUpdate.getItemStatus() != ItemStatus.PENDING){
            mediaToUpdate.setItemStatus(ItemStatus.PENDING);
            mediaToUpdate.setIsTagged(false);
        }
        return mediaRepo.save(mediaToUpdate);
    }
    public List<Media> updateTagStatus(List<Media> newTagged, List<Media> oldTagged) {

        for(Media oldMedia : oldTagged){
            log.info("Updating oldMedia: {}", oldMedia);
            oldMedia.setIsTagged(false);
            mediaRepo.save(oldMedia);
        }

        List<Media> confirmationMedia = new LinkedList<>();
        for(Media newMedia : newTagged){
            log.info("Updating newMedia: {}", newMedia);
            newMedia.setIsTagged(true);
            newMedia.setUpdatedAt(LocalDateTime.now());
            confirmationMedia.add(mediaRepo.save(newMedia));
        }

        return confirmationMedia;

    }
    //DELETE
    public void deleteMedia(UUID mediaId){
        log.info("deleting existing media entry");
        //TODO: need to add handling once media is tied to posts/users/display/etc
        mediaRepo.deleteById(mediaId);
    }
    //PROTECTED
    //PRIVATE
}
