package com.cs5324.monitorbackend.responsebody;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PostResponse {
    private UUID id;
    private UUID createdBy;
    private String title;
    private String content;
    private Media media; //TODO MediaResponse
    private ItemStatus status;
    private Boolean isTagged;
    private LocalDateTime createdOn;
}
