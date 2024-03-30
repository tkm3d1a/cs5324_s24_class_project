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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    //READ
    public List<Media> getAllMediaSortByCreatedDesc(){
        return mediaRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
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
    //DELETE

    //PROTECTED
    //PRIVATE
}
