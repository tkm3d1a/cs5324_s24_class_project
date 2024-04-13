package com.cs5324.monitorbackend.entity;

import lombok.Data;

import java.util.List;

@Data
public class IdListDTO {
    private int count;
    private List<String> ids;
}
