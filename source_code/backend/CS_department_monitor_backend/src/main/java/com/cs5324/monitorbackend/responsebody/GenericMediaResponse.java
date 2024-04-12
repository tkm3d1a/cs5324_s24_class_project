package com.cs5324.monitorbackend.responsebody;

import com.cs5324.monitorbackend.entity.Media;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GenericMediaResponse {
    private List<UUID> mediaIds;
    private int count;
    private List<Media> media;
}
