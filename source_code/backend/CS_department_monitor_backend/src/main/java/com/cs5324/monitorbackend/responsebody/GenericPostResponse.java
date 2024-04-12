package com.cs5324.monitorbackend.responsebody;

import com.cs5324.monitorbackend.entity.Post;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GenericPostResponse {
    private List<UUID> postIds;
    private int count;
    private List<Post> posts;
}
