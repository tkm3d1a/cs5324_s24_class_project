package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.MediaDTO;
import com.cs5324.monitorbackend.entity.enums.MediaType;
import com.cs5324.monitorbackend.exception.InvalidMediaTypeException;
import com.cs5324.monitorbackend.repository.MediaRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //DELETE

    //PROTECTED
    //PRIVATE
}
