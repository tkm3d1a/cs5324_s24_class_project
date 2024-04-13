package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.MediaDTO;
import com.cs5324.monitorbackend.responsebody.GenericMediaResponse;
import com.cs5324.monitorbackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/media", produces = "application/json")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @GetMapping
    public ResponseEntity<?> getAllMediaDefault(){
        Map<String,Object> response = new LinkedHashMap<>();
        List<Media> allMedia = mediaService.getAllMediaSortByCreatedDesc();
        response.put("count",allMedia.size());
        response.put("media",allMedia);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{mediaId}")
    public ResponseEntity<?> getMedia(@PathVariable String mediaId){
        Map<String,Object> response = new LinkedHashMap<>();
        UUID mediaIdConverted;
        try {
            mediaIdConverted = UUID.fromString(mediaId);
        } catch (Exception e) {
            log.error("error in getMedia: {}",e.getMessage());
            response.put("error","invalid UUID passed in URL path");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("result", "successful");
        response.put("media", mediaService.getMediaById(mediaIdConverted));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createNewMedia(@RequestBody MediaDTO mediaDTO){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("result", "successful");
        response.put("savedMedia", mediaService.createNewMediaEntry(mediaDTO));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/populate")
    public ResponseEntity<?> populateMedia(@RequestParam int mediaCount){
        GenericMediaResponse mediaResponse = new GenericMediaResponse();
        List<Media> populatedMedia = mediaService.populate(mediaCount);
        List<UUID> mediaIds = new ArrayList<>();
        for(Media pMedia : populatedMedia){
            mediaIds.add(pMedia.getId());
        }

        mediaResponse.setMediaIds(mediaIds);
        mediaResponse.setMedia(populatedMedia);
        return ResponseEntity.ok().body(mediaResponse);
    }

    @PatchMapping("/{mediaId}")
    public ResponseEntity<?> updateMedia(@PathVariable String mediaId, @RequestBody MediaDTO mediaDTO){
        Map<String,Object> response = new LinkedHashMap<>();
        UUID mediaIdConverted;
        try {
            mediaIdConverted = UUID.fromString(mediaId);
        } catch (Exception e) {
            log.error("error in updateMedia: {}", e.getMessage());
            response.put("result", "error");
            response.put("msg","invalid UUID passed in URL path");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("result", "successful");
        response.put("updatedMedia", mediaService.updateMedia(mediaIdConverted,mediaDTO));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<?> deleteMedia(@PathVariable String mediaId){
        Map<String,Object> response = new LinkedHashMap<>();
        UUID mediaIdConverted;
        try {
            mediaIdConverted = UUID.fromString(mediaId);
            mediaService.deleteMedia(mediaIdConverted);
        } catch (Exception e) {
            log.error("error in deleteMedia: {}",e.getMessage());
            response.put("error","invalid UUID passed in URL path");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("result", "successful");
        response.put("msg", "media with ID "+mediaId+" deleted");
        return ResponseEntity.ok().body(response);
    }
}
