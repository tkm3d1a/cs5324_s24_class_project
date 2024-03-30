package com.cs5324.monitorbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MediaDTO {
    @JsonProperty(value = "title", required = true)
    private String title;
    @JsonProperty(value = "type", required = true)
    private String mediaType;
    @JsonProperty(value = "link", required = true)
    private String link;
}
