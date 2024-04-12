package com.cs5324.monitorbackend.entity;

import lombok.Data;

import java.util.List;

@Data
public class MediaIdListDTO {
    private int count;
    private List<String> newMediaIds;
}
